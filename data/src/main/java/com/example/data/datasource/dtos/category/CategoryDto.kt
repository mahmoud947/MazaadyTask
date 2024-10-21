package com.example.data.datasource.dtos.category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class CategoryDto(
    val children: List<ChildrenDto?>?,
    @SerializedName("created_at")
    val circleIcon: String?,
    val description: Any?,
    @SerializedName("display_mode")
    val disableShipping: Int?,
    val id: Int?,
    val image: String?,
    val name: String?,
    val slug: String?
)