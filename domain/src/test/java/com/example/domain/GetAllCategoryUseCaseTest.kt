package com.example.domain

import com.example.core.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.Exception
import com.example.domain.models.Category
import com.example.domain.repository.CategoryRepository
import com.example.domain.usecase.GetAllCategoryUseCase

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllCategoryUseCaseTest {

    private lateinit var repository: CategoryRepository

    private lateinit var getAllCategoryUseCase: GetAllCategoryUseCase

    private val mockCategoryList = listOf(Category(listOf(), "Cars",null,null,null,null,null,null), Category(listOf(), "Furniture",null,null,null,null,null,null))

    @Before
    fun setUp() {
        repository = mockk()
        getAllCategoryUseCase = GetAllCategoryUseCase(repository)
    }

    @Test
    fun `invoke() returns success when repository call succeeds`() = runTest {
        coEvery { repository.getAllCategory() } returns mockCategoryList

        val result = getAllCategoryUseCase.invoke().toList()

        assertEquals(Resource.Loading, result[0])
        assertEquals(Resource.Success(mockCategoryList), result[1])
    }

    @Test
    fun `invoke() returns error when repository call throws exception`() = runTest {

        val exception = Exception("Network Error")
        coEvery { repository.getAllCategory() } throws exception

        val result = getAllCategoryUseCase.invoke().toList()

        assertEquals(Resource.Loading, result[0])
        assert(result[1] is Resource.Error)
    }
}