package com.Frndzcart.urbanchoice.model

data class ProductResponse(
        val `data`: List<ProductResponseItem>,
        val message: String,
        val status: String
)