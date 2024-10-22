package com.example.domain.repository

import com.example.domain.models.Category
import com.example.domain.models.Properties


interface CategoryRepository {

    suspend fun getAllCategory(): List<Category>

    suspend fun getSubCategories(
        categoryId: Int
    ): List<Properties>

    suspend fun getOptionsChildByParentId(
        parentId: Int
    ): List<Properties>
}