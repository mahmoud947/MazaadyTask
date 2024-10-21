package com.example.domain.models


data class Properties(
    val description: String?,
    val id: Int?,
    val list: Boolean?,
    val name: String?,
    val options: List<Option?>?,
    val otherValue: Any?,
    val parent: Any?,
    val slug: String?,
    val type: String?,
    val value: String?
)