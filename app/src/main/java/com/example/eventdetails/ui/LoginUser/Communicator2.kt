package com.example.eventdetails.ui.LoginUser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Communicator2 : ViewModel(){
    val name = MutableLiveData<Any>()
    val birthday = MutableLiveData<Any>()
    val contact = MutableLiveData<Any>()
    val email = MutableLiveData<Any>()

    fun setMsgCommunicator(msg1:String, msg2:String, msg3:String, msg4:String){
        name.value = msg1
        birthday.value = msg2
        contact.value = msg3
        email.value = msg4
    }

    fun setEmailCommunicator(msg1:String){
        email.value = msg1
    }
}