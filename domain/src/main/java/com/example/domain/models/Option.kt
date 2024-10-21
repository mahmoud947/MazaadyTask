package com.example.domain.models



data class Option(
    val child: Boolean?,
    override val id: Int?,
    override val name: String?,
    val parent: Int?,
    override val slug: String?
): BottomSheetItem