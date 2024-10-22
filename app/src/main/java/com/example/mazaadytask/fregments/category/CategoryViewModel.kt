package com.example.mazaadytask.fregments.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.base.BaseViewModel
import com.example.core.utils.Resource
import com.example.domain.errors.ExceptionHandler
import com.example.domain.models.Category
import com.example.domain.models.Properties
import com.example.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {
    private val _categories = MutableLiveData<Resource<List<Category>>>()
    val categories: LiveData<Resource<List<Category>>> get() = _categories

    private val _subCategories = MutableLiveData<Resource<List<Properties>>>()
    val subCategories: LiveData<Resource<List<Properties>>> get() = _subCategories

    private val _optionChild = MutableLiveData<Resource<List<Properties>>>()
    val optionChild: LiveData<Resource<List<Properties>>> get() = _optionChild

    private val _map : MutableMap<String, String> = mutableMapOf()
    val map : Map<String, String> get() = _map


    fun addMap(key: String, value: String){
        _map[key] = value
    }

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

    fun getSubCategories(id: Int) {
        launchCoroutine(Dispatchers.IO) {
            _categories.postValue(Resource.Loading)
            try {
                val response = categoryRepository.getPropertiesByCategory(id)
                _subCategories.postValue(Resource.Success(response))
            } catch (e: Exception) {
                _subCategories.postValue(ExceptionHandler.resolveError(e))
            }
        }
    }

    fun getOptionChild(id: Int){
        launchCoroutine(Dispatchers.IO){
            _optionChild.postValue(Resource.Loading)
            try {
                val response = categoryRepository.getOptionsChildByParentId(id)
                _optionChild.postValue(Resource.Success(response))
            }catch (e: Exception) {
                _optionChild.postValue(ExceptionHandler.resolveError(e))
            }
        }
    }




}