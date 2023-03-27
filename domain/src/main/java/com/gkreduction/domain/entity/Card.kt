package com.gkreduction.domain.entity

data class Card(
    var category: Category,
    var cardId: Long,
    var cardName: String = "",
    var cardBaseInfo: String = "",
    var cardSecondInfo: String = "",
    var primary: ScanCode,
    var existSecondary: Boolean,
    var secondary: ScanCode
)