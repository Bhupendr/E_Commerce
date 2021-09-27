package com.Frndzcart.frndzcart.model

data class OrderResponse(
        val `data`: List<OrderData>,
        val message: String,
        val status: String
)