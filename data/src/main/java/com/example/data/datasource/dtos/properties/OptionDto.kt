package com.example.data.datasource.dtos.properties

import androidx.annotation.Keep


@Keep
data class OptionDto(
    val child: Boolean?,
    val id: Int?,
    val name: String?,
    val parent: Int?,
    val slug: String?
)