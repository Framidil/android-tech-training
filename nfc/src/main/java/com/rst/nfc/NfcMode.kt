package com.rst.nfc

enum class NfcMode {
    READ_NDEF,
    READ_FULL_RAW,
    WRITE_WITHOUT_PASSWORD,
    WRITE_WITH_PASSWORD,
    SET_PASSWORD,
    REMOVE_PASSWORD,
}

object NfcModeHelper {
    val nfcModes = listOf(
        NfcMode.READ_NDEF,
        NfcMode.READ_FULL_RAW,
        NfcMode.WRITE_WITHOUT_PASSWORD,
        NfcMode.WRITE_WITH_PASSWORD,
        NfcMode.SET_PASSWORD,
        NfcMode.REMOVE_PASSWORD,
    )

    val nfcModesString = mapOf(
        NfcMode.READ_NDEF to "Считать ссылку",
        NfcMode.READ_FULL_RAW to "Считать все байты",
        NfcMode.WRITE_WITHOUT_PASSWORD to "Записать без пароля",
        NfcMode.WRITE_WITH_PASSWORD to "Записать с паролем",
        NfcMode.SET_PASSWORD to "Установить пароль",
        NfcMode.REMOVE_PASSWORD to "Удалить пароль",
    )
}

