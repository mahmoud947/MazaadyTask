package com.example.mazaadytask.fregments.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.base.BaseViewModel
import com.example.core.utils.Resource
import com.example.domain.errors.ExceptionHandler
import com.example.domain.models.Category
import com.example.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {
    private var _categories = MutableLiveData<Resource<List<Category>>>()
    val categories: LiveData<Resource<List<Category>>> get() = _categories

    fun getCategories() {
        launchCoroutine(Dispatchers.IO) {
            _categories.postValue(Resource.Loading)
            try {
                val response = categoryRepository.getAllCategory()
                _categories.postValue(Resource.Success(response))
            } catch (e: Exception) {
                _categories.postValue(ExceptionHandler.resolveError(e))
            }
        }
    }


}