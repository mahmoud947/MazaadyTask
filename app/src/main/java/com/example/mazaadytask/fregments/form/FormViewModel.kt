package com.example.mazaadytask.fregments.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.base.BaseViewModel
import com.example.core.utils.ErrorModel
import com.example.core.utils.Resource
import com.example.core.utils.Resource.*
import com.example.domain.errors.ExceptionHandler
import com.example.domain.models.Category
import com.example.domain.models.Properties
import com.example.domain.repository.CategoryRepository
import com.example.domain.usecase.GetAllCategoryUseCase
import com.example.domain.usecase.GetOptionChildUseCase
import com.example.domain.usecase.GetSubCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val getSubCategoriesUseCase: GetSubCategories,
    private val getOptionChildUseCase: GetOptionChildUseCase
) : BaseViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private val _subCategories = MutableLiveData<List<Properties>>()
    val subCategories: LiveData<List<Properties>> get() = _subCategories

    private val _optionChild = MutableLiveData<List<Properties>>()
    val optionChild: LiveData<List<Properties>> get() = _optionChild

    private val _map: MutableMap<String, String> = mutableMapOf()
    val map: Map<String, String> get() = _map


    fun addMap(key: String, value: String) {
        _map[key] = value
    }

    fun getCategories() {
        launchCoroutine {
            getAllCategoryUseCase()
                .flowOn(Dispatchers.IO)
                .collectLatest {
                when (it) {
                    is Success -> {
                        _categories.postValue(it.data)
                        showLoading.postValue(false)
                    }

                    is Error -> {
                        showErrorMessage.postValue(
                            ErrorModel(
                                it.exception.message.toString(),
                                "Ok"
                            )
                        )
                    }

                    is Loading -> {
                        showLoading.postValue(true)
                    }

                    Idle -> {
                        showLoading.postValue(false)
                    }
                }
            }
        }
    }

    fun getSubCategories(id: Int) {
        launchCoroutine {
            getSubCategoriesUseCase(id)
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    when (it) {
                        is Success -> {
                            _subCategories.postValue(it.data)
                            showLoading.postValue(false)
                        }

                        is Error -> {
                            showErrorMessage.postValue(
                                ErrorModel(
                                    it.exception.message.toString(),
                                    "Ok"
                                )
                            )
                        }

                        is Loading -> {
                            showLoading.postValue(true)
                        }

                        Idle -> {
                            showLoading.postValue(false)
                        }
                    }

                }
        }
    }

    fun getOptionChild(id: Int) {
        launchCoroutine {
            getOptionChildUseCase(id)
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    when (it) {
                        is Success -> {
                            _optionChild.postValue(it.data)
                            showLoading.postValue(false)
                        }

                        is Error -> {
                            showErrorMessage.postValue(
                                ErrorModel(
                                    it.exception.message.toString(),
                                    "Ok"
                                )
                            )
                        }

                        is Loading -> {
                            showLoading.postValue(true)
                        }

                        Idle -> {
                            showLoading.postValue(false)
                        }
                    }
                }
        }
    }


}