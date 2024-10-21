package com.example.mapper

import com.example.data.datasource.dtos.properties.PropertiesDto
import com.example.domain.models.Properties

fun PropertiesDto.toDomain(): Properties = Properties(
    id = id,
    description = description,
    list = list,
    name = name,
    options = optionDtos?.map { it?.toDomain() },
    otherValue = otherValue,
    parent = parent,
    slug = slug,
    type = type,
    value = value
)