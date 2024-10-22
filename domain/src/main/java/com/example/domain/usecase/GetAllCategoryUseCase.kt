package com.example.domain.usecase

import com.example.core.base.BaseOUseCase
import com.example.core.utils.Resource
import com.example.domain.errors.ExceptionHandler
import com.example.domain.models.Category
import com.example.domain.models.Properties
import com.example.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetAllCategoryUseCase constructor(
    private val repository: CategoryRepository
): BaseOUseCase<Flow<Resource<List<Category>>>> {
    override fun invoke(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading)
            val response = repository.getAllCategory()
            emit(Resource.Success(response))
    }.catch {
        emit(ExceptionHandler.resolveError(it))
    }
}