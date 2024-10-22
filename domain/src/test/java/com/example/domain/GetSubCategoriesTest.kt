package com.example.domain

import com.example.core.utils.Resource
import com.example.domain.models.Properties
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.example.domain.repository.CategoryRepository
import com.example.domain.usecase.GetSubCategories

@OptIn(ExperimentalCoroutinesApi::class)
class GetSubCategoriesTest {

    private lateinit var repository: CategoryRepository

    private lateinit var getSubCategories: GetSubCategories

    private val mockSubCategoriesList = listOf(
        Properties("SubCategory 1", 1, null, null, null, null, null, null, null, null),
        Properties("SubCategory 2", 2, null, null, null, null, null, null, null, null)
    )

    @Before
    fun setUp() {
        repository = mockk()
        getSubCategories = GetSubCategories(repository)
    }

    @Test
    fun `invoke() returns success when repository call succeeds`() = runTest {
        val categoryId = 456
        coEvery { repository.getSubCategories(categoryId) } returns mockSubCategoriesList

        val result = getSubCategories.invoke(categoryId).toList()

        assertEquals(Resource.Loading, result[0])
        assertEquals(Resource.Success(mockSubCategoriesList), result[1])
    }

    @Test
    fun `invoke() returns error when repository call throws exception`() = runTest {
        val categoryId = 456
        val exception = Exception("Network Error")
        coEvery { repository.getSubCategories(categoryId) } throws exception

        val result = getSubCategories.invoke(categoryId).toList()

        assertEquals(Resource.Loading, result[0])
        assert(result[1] is Resource.Error)
    }
}