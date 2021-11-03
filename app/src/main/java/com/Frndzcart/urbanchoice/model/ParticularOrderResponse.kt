package com.Frndzcart.urbanchoice.model

data class ParticularOrderResponse(
    val address: String,
    val created_at: String,
    val customer_id: String,
    val deliveryfee: String,
    val id: String,
    val item_details: List<ItemDetail>,
    val order_id: String,
    val status: String,
    val total_amount: String
)