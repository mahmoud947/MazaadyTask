package com.example.mazaadytask.fregments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.mazaadytask.R
@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {
    private val _story: MutableLiveData<List<Int>> = MutableLiveData()
    val story: LiveData<List<Int>> = _story

    fun fetchStory() {
        _story.value = listOf(
            R.drawable.person_1,
            R.drawable.person_2,
            R.drawable.person_3,
            R.drawable.person_4,
            R.drawable.person_1,
            R.drawable.person_2,
            R.drawable.person_3,
            R.drawable.person_4,
            R.drawable.person_1,
            R.drawable.person_2,
            R.drawable.person_3,
            R.drawable.person_4,
        )
    }
}