package com.example.domain.models




data class Category(
    val children: List<Children?>?,
    val circleIcon: String?,
    val description: Any?,
    val disableShipping: Int?,
    val id: Int?,
    val image: String?,
    val name: String?,
    val slug: String?
)