package com.example.domain.models

import androidx.annotation.Keep


data class Children(
    val children: Any?,
    val circleIcon: String?,
    val description: Any?,
    val disableShipping: Int?,
    override val id: Int?,
    val image: String?,
    override val name: String?,
    override val slug: String?
): BottomSheetItem