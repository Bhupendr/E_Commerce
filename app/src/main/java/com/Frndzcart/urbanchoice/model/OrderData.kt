package com.Frndzcart.urbanchoice.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class OrderData(
    val address: String = "",
    val created_at: String = "",
    val customer_id: String= "",
    val id: String= "",
    val item_details: String= "",
    val order_id: String= "",
    val status: String= "",
    val total_amount: String= ""
) : Parcelable