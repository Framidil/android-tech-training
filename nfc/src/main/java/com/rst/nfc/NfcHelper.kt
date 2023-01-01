package com.rst.nfc

import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NfcA
import timber.log.Timber
import kotlin.experimental.and

class NfcHelper(private val tag: Tag) {
    companion object {
        private val charset = Charsets.UTF_8
    }

    private val transceiveHelper = NfcATransceiveHelper(NfcNtagType.NTAG_213)

    fun readAsNdef(): String {
        val ndefMessage = Ndef.get(tag).cachedNdefMessage
        val records = ndefMessage.records

        records.filter { ndefRecord ->
            ndefRecord.tnf == NdefRecord.TNF_WELL_KNOWN &&
                    ndefRecord.type.contentEquals(NdefRecord.RTD_TEXT)
        }.forEach { ndefRecord ->
            val payload = ndefRecord.payload
            val languageCodeLength = payload[0] and 51

            return String(
                payload,
                languageCodeLength + 1,
                payload.size - languageCodeLength - 1,
                charset
            )
        }

        return "Произошла ошибка"
    }

    fun readRawAllBytes(): String {
        return withNfcATag {
            val bytes = transceiveHelper.readRawAllBytes(it)
            var i = 0
            Timber.d(bytes.joinToString { b -> "${i++}[$b] " })
            String(bytes, Charsets.UTF_8)
        }.also { res ->
            Timber.d("newRead result: $res")
        }
    }

    fun writeWithoutPassword(url: String): String {
        if (!itsHttpsUrl(url)) {
            return "Неверный формат ссылки"
        }
        val bytesToWrite = httpsUrlToNdefBytes(url)
        assert(bytesToWrite.size % 4 == 0) { "The number of characters must be divisible by the page size" }

        withNfcATag {
            transceiveHelper.writeBytesFromStart(it, bytesToWrite)
        }

        return "Успешно записано"
    }

    fun writeWithPassword(url: String, password: String): String {
        if (!itsHttpsUrl(url)) {
            return "Неверный формат ссылки"
        }
        val bytesToWrite = httpsUrlToNdefBytes(url)
        assert(bytesToWrite.size % 4 == 0) { "The number of characters must be divisible by the page size" }

        val pwdBytes = getPwdBytes(password)

        withNfcATag {
            if (transceiveHelper.passwordNotSet(it)) {
                transceiveHelper.writeBytesFromStart(it, bytesToWrite)
                transceiveHelper.setPassword(it, pwdBytes)
            } else {
                transceiveHelper.writePwdAuth(it, pwdBytes)
                transceiveHelper.writeBytesFromStart(it, bytesToWrite)
            }
        }

        return "Успешно записано"
    }

    private fun itsHttpsUrl(url: String) = url.startsWith("https://")

    private fun httpsUrlToNdefBytes(url: String): List<Byte> {
        val rawUrlText = url.removePrefix("https://")
        val rawUrlBytes = rawUrlText.map { it.code.toByte() }.toTypedArray()

        return getUrlNdefMessageBytes(rawUrlBytes)
    }

    private fun getUrlNdefMessageBytes(rawUrlBytes: Array<Byte>): List<Byte> {
        val rawUrlLength = rawUrlBytes.size

        return mutableListOf(
            1.toByte(),
            3.toByte(),
            (-96).toByte(),
            12.toByte(),
            52.toByte(),
            3.toByte(),
            (rawUrlLength + 5).toByte(),// !
            (-47).toByte(),
            1.toByte(),
            (rawUrlLength + 1).toByte(),// !
            85.toByte(),
            4.toByte(),
            *rawUrlBytes,               // !
            (-2).toByte(),
        ).apply {
            while (size % 4 != 0) {
                add(0.toByte())
            }
        }
    }

    fun setPassword(password: String): String {
        val pwdBytes = getPwdBytes(password)
        withNfcATag {
            transceiveHelper.setPassword(it, pwdBytes)
        }

        return "Пароль установлен"
    }

    fun removePassword(password: String): String {
        val pwdBytes = getPwdBytes(password)
        withNfcATag {
            transceiveHelper.removePassword(it, pwdBytes)
        }

        return "Пароль удален"
    }

    private fun <T> withNfcATag(onNfcATag: (NfcA) -> T): T {
        val nfcATag = NfcA.get(tag)
        return nfcATag.use {
            it.connect()
            onNfcATag(nfcATag)
        }
    }

    private fun getPwdBytes(password: String): List<Byte> {
        return password.take(4).toByteArray(Charsets.US_ASCII).toList()
    }
}
