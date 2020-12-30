package com.eric.startupmatching

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainActivityViewModel(): ViewModel() {

    val fragmentType = MutableLiveData<String>()

}