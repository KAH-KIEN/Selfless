package com.example.eventdetails.ui.EventDetails


import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

class Communicator : ViewModel(){


    val title = MutableLiveData<Any>()
    val date = MutableLiveData<Any>()
    val time = MutableLiveData<Any>()
    val location = MutableLiveData<Any>()
    val phoneNum = MutableLiveData<Any>()
    val desc = MutableLiveData<Any>()
    val eventID = MutableLiveData<Any>()

    fun setMsgCommunicator(msg1:String, msg2:String, msg3:String, msg4:String, msg5:String, msg6:String){
        title.value = msg1
        date.value = msg2
        time.value = msg3
        location.value = msg4
        phoneNum.value = msg5
        desc.value = msg6
    }

    fun passID(eventID : String) {
        this.eventID.value = eventID
    }

    fun receiveID() : Any? {
        return this.eventID.value
    }

    fun generateQRCode(text: String): Bitmap {
        val width = 120
        val height = 120
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()
        try {
            val bitMatrix = codeWriter.encode(text, BarcodeFormat.QR_CODE, width,height)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) {
            Log.d(ContentValues.TAG, "generateQRCode: ${e.message}")
        }
        return bitmap
    }
}