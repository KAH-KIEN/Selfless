package com.example.eventdetails.ui.EventDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventDetailsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is events Fragment"
    }
    val text: LiveData<String> = _text
}