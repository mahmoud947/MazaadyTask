package com.example.data.repository;

import com.example.core.base.BaseResponse
import com.example.data.datasource.dtos.category.CategoryDto
import com.example.data.datasource.dtos.category.CategoryRes
import com.example.data.datasource.dtos.properties.PropertiesDto
import com.example.data.datasource.remote.CategoryService
import com.example.data.repositories.CategoryRepositoryImpl;

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CategoryRepositoryImplTest {

    private lateinit var repository: CategoryRepositoryImpl
    private val mockService = mockk<CategoryService>()
    
    @Before
    fun setUp() {
        repository = CategoryRepositoryImpl(mockService)
    }

    @Test
    fun `test getAllCategory success`() = runTest {
        val mockCategories = listOf(CategoryDto(id = 1, name = "Test Category", slug = "test", children = null, circleIcon = null, disableShipping = 0, description = null, image = null))
        coEvery { mockService.getAllCategory() } returns BaseResponse(code = 200, msg = "Success", data = CategoryRes(categories = mockCategories))

        val result = repository.getAllCategory()

        // Assert: Verify the result
        assertEquals(1, result.size)
        assertEquals("Test Category", result[0].name)

        verify { runBlocking { mockService.getAllCategory() } }
    }

    @Test
    fun `test getSubCategories success`() = runTest {
        val mockProperties = listOf(PropertiesDto(id = 1, name = "SubCategory", description = "Description", options = emptyList(), parent = null, slug = "subcategory", value = "", otherValue = null, type = null, list = false))
        coEvery { mockService.getSubCategories(any()) } returns BaseResponse(code = 200, msg = "Success", data = mockProperties)

        val result = repository.getSubCategories(1)

        assertEquals(1, result.size)
        assertEquals("SubCategory", result[0].name)

        verify { runBlocking { mockService.getSubCategories(1) } }
    }

    @Test
    fun `test getOptionsChildByParentId success`() = runTest {
        val mockOptions = listOf(PropertiesDto(id = 1, name = "OptionChild", description = "Option Description", options = emptyList(), parent = null, slug = "option-child", value = "", otherValue = null, type = null, list = false))
        coEvery { mockService.getOptionsChildByParentId(any()) } returns BaseResponse(code = 200, msg = "Success", data = mockOptions)

        val result = repository.getOptionsChildByParentId(1)

        assertEquals(1, result.size)
        assertEquals("OptionChild", result[0].name)

        verify { runBlocking { mockService.getOptionsChildByParentId(1) } }
    }
}