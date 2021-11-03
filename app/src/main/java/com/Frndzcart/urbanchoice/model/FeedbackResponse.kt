package com.Frndzcart.urbanchoice.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FeedbackResponse(
    val customer_id: Int = 0,
    val description: String = "",
    val rating: String = "",
    val title: String= ""
) : Parcelable