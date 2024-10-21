package com.example.mapper

import com.example.data.datasource.dtos.category.CategoryDto
import com.example.domain.models.Category
import com.example.domain.models.Children
import kotlin.collections.List

fun CategoryDto.toDomain(): Category = Category(
    children = children?.map { it?.toDomain() },
    circleIcon = circleIcon,
    description = description,
    disableShipping = disableShipping,
    id = id,
    image = image,
    name = name,
    slug = slug,
)