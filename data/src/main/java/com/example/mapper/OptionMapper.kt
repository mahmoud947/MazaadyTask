package com.example.mapper

import com.example.data.datasource.dtos.properties.OptionDto
import com.example.domain.models.Option

fun OptionDto.toDomain(): Option = Option(
    id = id,
    name = name,
    child = child,
    parent = parent,
    slug = slug
)