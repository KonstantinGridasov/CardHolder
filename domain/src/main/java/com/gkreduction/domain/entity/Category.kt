package com.gkreduction.domain.entity

import java.io.Serializable

data class Category(var catId: Long=0L, var catName: String="", var position: Long=0L) : Serializable