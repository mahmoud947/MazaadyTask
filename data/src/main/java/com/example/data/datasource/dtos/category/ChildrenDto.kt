package com.example.data.datasource.dtos.category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class ChildrenDto(
    val children: Any?,
    @SerializedName("children_count")
    val circleIcon: String?,
    val description: Any?,
    @SerializedName("disable_shipping")
    val disableShipping: Int?,
    val id: Int?,
    val image: String?,
    val name: String?,
    val slug: String?
)