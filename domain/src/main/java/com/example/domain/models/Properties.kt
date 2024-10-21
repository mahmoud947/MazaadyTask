package com.example.domain.models


data class Properties(
    val description: String?,
    override val id: Int?,
    val list: Boolean?,
    override val name: String?,
    val options: List<Option?>?,
    val otherValue: Any?,
    val parent: Any?,
    override val slug: String?,
    val type: String?,
    val value: String?
): BottomSheetItem