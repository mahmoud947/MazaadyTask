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
import com.example.domain.usecase.GetOptionChildUseCase

@OptIn(ExperimentalCoroutinesApi::class)
class GetOptionChildUseCaseTest {

    private lateinit var repository: CategoryRepository

    private lateinit var getOptionChildUseCase: GetOptionChildUseCase

    private val mockPropertiesList = listOf(Properties( "Option 1",1,null,null,null,null,null,null,null,null), Properties( "Option 2",null,null,null,null,null,null,null,null,null))

    @Before
    fun setUp() {
        repository = mockk()
        getOptionChildUseCase = GetOptionChildUseCase(repository)
    }

    @Test
    fun `invoke() returns success when repository call succeeds`() = runTest {
        val parentId = 123
        coEvery { repository.getOptionsChildByParentId(parentId) } returns mockPropertiesList

        val result = getOptionChildUseCase.invoke(parentId).toList()

        assertEquals(Resource.Loading, result[0])
        assertEquals(Resource.Success(mockPropertiesList), result[1])
    }

    @Test
    fun `invoke() returns error when repository call throws exception`() = runTest {
        val parentId = 123
        val exception = Exception("Network Error")
        coEvery { repository.getOptionsChildByParentId(parentId) } throws exception

        val result = getOptionChildUseCase.invoke(parentId).toList()

        assertEquals(Resource.Loading, result[0])
        assert(result[1] is Resource.Error)
    }
}