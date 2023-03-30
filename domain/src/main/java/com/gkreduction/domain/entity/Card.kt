package com.gkreduction.domain.entity

data class Card(
    var category: Category,
    var color: Int,
    var countOpen: Int = 0,
    var cardId: Long = 0L,
    var cardName: String = "",
    var cardBaseInfo: String = "",
    var cardSecondInfo: String = "",
    var primary: ScanCode,
    var existSecondary: Boolean,
    var secondary: ScanCode
)