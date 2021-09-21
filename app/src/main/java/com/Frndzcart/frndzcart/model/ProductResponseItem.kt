package com.Frndzcart.frndzcart.model

data class ProductResponseItem(
    val added_on: String,
    val category_id: String,
    val icon_file: String,
    val id: String,
    val inventory: String,
    val mrp: String,
    val price: String,
    val status: String,
    val title: String,
    var quantity: Int = 1
)