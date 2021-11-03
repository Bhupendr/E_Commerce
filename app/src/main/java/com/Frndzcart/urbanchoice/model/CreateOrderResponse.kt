package com.Frndzcart.urbanchoice.model

data class CreateOrderResponse(
    val address: String = "",
    val customer_id: Int = 0,
    val deliveryfee: Int = 0,
    val items: List<Item>,
    val total_amount: Int = 0
)