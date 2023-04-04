package com.gkreduction.domain.entity

import java.io.Serializable

data class Category(var catId: Long, var catName: String, var position: Long) : Serializable