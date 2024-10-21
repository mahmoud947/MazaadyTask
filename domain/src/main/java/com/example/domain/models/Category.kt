package com.example.domain.models




data class Category(
    val children: List<Children?>?,
    val circleIcon: String?,
    val description: Any?,
    val disableShipping: Int?,
    override val id: Int?,
    val image: String?,
    override val name: String?,
    override val slug: String?
): BottomSheetItem