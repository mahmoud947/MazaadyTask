package com.example.domain.usecase

import com.example.core.base.BaseIOUseCase
import com.example.core.base.BaseOUseCase
import com.example.core.utils.Resource
import com.example.domain.errors.ExceptionHandler
import com.example.domain.models.Category
import com.example.domain.models.Properties
import com.example.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetOptionChildUseCase constructor(
    private val repository: CategoryRepository
): BaseIOUseCase<Int,Flow<Resource<List<Properties>>>> {
    override fun invoke(input: Int): Flow<Resource<List<Properties>>> {
        return flow {
            emit(Resource.Loading)
            val response = repository.getOptionsChildByParentId(input)
            emit(Resource.Success(response))
        }.catch {
            emit(ExceptionHandler.resolveError(it))
        }
    }

}