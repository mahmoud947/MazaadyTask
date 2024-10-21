package com.example.mapper

import com.example.data.datasource.dtos.category.ChildrenDto
import com.example.domain.models.Children

fun ChildrenDto.toDomain(): Children = Children(
    children = children,
    circleIcon = circleIcon,
    description = description,
    disableShipping = disableShipping,
    id = id,
    image = image,
    name = name,
    slug = slug
)