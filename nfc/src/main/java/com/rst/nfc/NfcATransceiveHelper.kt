package com.rst.nfc

import android.nfc.tech.NfcA

class NfcATransceiveHelper(private val ntagType: NfcNtagType) {
    private companion object {
        private const val CMD_READ = 0x30.toByte()
        private const val CMD_FAST_READ = 0x3A.toByte()
        private const val CMD_WRITE = 0xA2.toByte()

        private const val CMD_PWD_AUTH = 0x1B.toByte()
    }

    private val lastPage = when (ntagType) {
        NfcNtagType.NTAG_213 -> 0x2C.toByte()
        NfcNtagType.NTAG_215 -> 0x86.toByte()
        NfcNtagType.NTAG_216 -> 0xE6.toByte()
    }

    private val FIRST_DATA_PAGE = (0x04).toByte()
    private val LAST_DATA_PAGE = lastPage

    private val AUTH0_PAGE = (lastPage - 3).toByte()
    private val PWD_PAGE = (lastPage - 1).toByte()
    private val PACK_PAGE = lastPage

    private val AUTH0_WITH_PASSWORD = 0x00.toByte()
    private val AUTH0_WITHOUT_PASSWORD = 0xFF.toByte()

    private val DEFAULT_PACK_VALUE = byteArrayOf(0x00, 0x00)


    fun readRawAllBytes(it: NfcA) = it.transceive(
        byteArrayOf(
            CMD_FAST_READ,
            FIRST_DATA_PAGE,
            LAST_DATA_PAGE
        )
    )

    fun passwordNotSet(nfcATag: NfcA): Boolean {
        val auth0Value = getAuth0Page(nfcATag)[3].toUByte()
        val lastPageValue = lastPage.toUByte()

        return auth0Value >= lastPageValue
    }

    fun writeBytesFromStart(nfcATag: NfcA, bytesToWrite: List<Byte>) {
        repeat(bytesToWrite.size / 4) { index ->
            val newBytes = arrayOf(
                bytesToWrite[4 * index],
                bytesToWrite[4 * index + 1],
                bytesToWrite[4 * index + 2],
                bytesToWrite[4 * index + 3],
            )

            nfcATag.transceive(
                byteArrayOf(
                    CMD_WRITE,
                    (FIRST_DATA_PAGE + index).toByte(),
                    newBytes[0], newBytes[1], newBytes[2], newBytes[3],
                )
            )
        }
    }

    fun setPassword(nfcATag: NfcA, pwdBytes: List<Byte>) {
        val prevAuth0Page = getAuth0Page(nfcATag)

        writePwd(nfcATag, pwdBytes)
        writePack(nfcATag, DEFAULT_PACK_VALUE)

        writeAuth0(nfcATag, prevAuth0Page, AUTH0_WITH_PASSWORD)
    }

    fun removePassword(nfcATag: NfcA, pwdBytes: List<Byte>) {
        val prevAuth0Page = getAuth0Page(nfcATag)

        writePwdAuth(nfcATag, pwdBytes)

        writeAuth0(nfcATag, prevAuth0Page, AUTH0_WITHOUT_PASSWORD)
    }

    fun writePwdAuth(nfcATag: NfcA, pwdBytes: List<Byte>) = nfcATag.transceive(
        byteArrayOf(
            CMD_PWD_AUTH,
            pwdBytes[0], pwdBytes[1], pwdBytes[2], pwdBytes[3]
        )
    )

    private fun writeAuth0(nfcATag: NfcA, prevAuth0Page: ByteArray, newAuth0Byte: Byte) =
        nfcATag.transceive(
            byteArrayOf(
                CMD_WRITE,
                AUTH0_PAGE,
                prevAuth0Page[0], prevAuth0Page[1], prevAuth0Page[2], newAuth0Byte
            )
        )

    private fun writePack(nfcATag: NfcA, packBytes: ByteArray) = nfcATag.transceive(
        byteArrayOf(
            CMD_WRITE,
            PACK_PAGE,
            packBytes[0], packBytes[1], 0, 0
        )
    )

    private fun writePwd(nfcATag: NfcA, pwdBytes: List<Byte>) = nfcATag.transceive(
        byteArrayOf(
            CMD_WRITE,
            PWD_PAGE,
            pwdBytes[0], pwdBytes[1], pwdBytes[2], pwdBytes[3],
        )
    )

    private fun getAuth0Page(it: NfcA) = it.transceive(
        byteArrayOf(
            CMD_READ,
            AUTH0_PAGE,
        )
    ).take(4).toByteArray()
}
