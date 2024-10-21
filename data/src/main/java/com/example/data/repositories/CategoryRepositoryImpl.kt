package com.example.data.repositories

import com.example.data.datasource.remote.CategoryService
import com.example.domain.models.Category
import com.example.domain.models.Properties
import com.example.domain.repository.CategoryRepository
import com.example.mapper.toDomain
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryService
) : CategoryRepository {
    override suspend fun getAllCategory(): List<Category> =
        api.getAllCategory().data.categories.map { it.toDomain() }

    override suspend fun getPropertiesByCategory(categoryId: Int): List<Properties> =
        api.getPropertiesByCategory(categoryId).data.map { it.toDomain() }

    override suspend fun getOptionsChildByParentId(parentId: Int): List<Properties> =
        api.getOptionsChildByParentId(parentId).data.map { it.toDomain() }
}