package com.gkreduction.domain.entity

data class Card(
    var category: Category = Category(),
    var colorStart: Int = 0,
    var colorEnd: Int = 0,
    var countOpen: Int = 0,
    var cardId: Long = 0L,
    var cardName: String = "",
    var cardBaseInfo: String = "",
    var cardSecondInfo: String = "",
    var primary: ScanCode? = null,
    var existSecondary: Boolean = false,
    var secondary: ScanCode? = null,
    var createdAt: Long = 0L,
    var modifiedAt: Long = 0L
)