package com.example.domain.repository

import com.example.domain.models.Category
import com.example.domain.models.Properties
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


interface CategoryRepository {

    suspend fun getAllCategory(): List<Category>

    suspend fun getPropertiesByCategory(
        categoryId: Int
    ): List<Properties>

    suspend fun getOptionsChildByParentId(
        parentId: Int
    ): List<Properties>
}