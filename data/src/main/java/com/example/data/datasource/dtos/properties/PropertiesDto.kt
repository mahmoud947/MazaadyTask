package com.example.data.datasource.dtos.properties

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class PropertiesDto(
    val description: String?,
    val id: Int?,
    val list: Boolean?,
    val name: String?,
    val optionDtos: List<OptionDto?>?,
    @SerializedName("other_value")
    val otherValue: Any?,
    val parent: Any?,
    val slug: String?,
    val type: String?,
    val value: String?
)