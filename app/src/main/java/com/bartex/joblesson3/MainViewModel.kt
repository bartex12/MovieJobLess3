package com.bartex.joblesson3

import androidx.lifecycle.ViewModel

class MainViewModel(
    private val helper:IHelper = Helper(App.instance)
    ): ViewModel() {


    fun getIsClick(): Boolean {
        return helper.getIsClick()
    }

    fun saveIsClick(click: Boolean) {
        helper.saveIsClick(click)
    }

    override fun onCleared() {
        super.onCleared()
        helper.saveIsClick(false)
    }
}