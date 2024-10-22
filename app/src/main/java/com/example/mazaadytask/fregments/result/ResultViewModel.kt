package com.example.mazaadytask.fregments.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.base.BaseViewModel
import com.example.domain.models.ResultModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import javax.inject.Inject

class ResultViewModel @Inject constructor() : BaseViewModel() {
    val _result: MutableLiveData<List<ResultModel>> = MutableLiveData<List<ResultModel>>()
    val result: LiveData<List<ResultModel>> get() = _result
    val gson = Gson()

    fun extractArgs(json: String) {
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        val mapData: Map<String, String> = gson.fromJson(json, mapType)
        val list = mapData.map { ResultModel(it.key, it.value) }
        _result.value = list
    }

}