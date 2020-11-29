package com.example.eventdetails.ui.EventDetails

import android.graphics.Bitmap
import android.graphics.Color
import android.media.Image
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import com.example.eventdetails.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.encoder.QRCode
import java.util.Date.from

class QRCodeFragment : Fragment() {

    companion object {
        fun newInstance() = QRCodeFragment()
    }

    private lateinit var viewModel: QRCodeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.i("QRFragment","Created")
        val root = inflater.inflate(R.layout.q_r_code_fragment, container, false)
        val model= ViewModelProviders.of(requireActivity()).get(Communicator::class.java)
        val eventID = model.receiveID()
        //val bitmap = model.generateQRCode("12345")
        val imageViewQRCode : ImageView = root.findViewById(R.id.imageViewQRCode)
        /*imageViewQRCode.setImageBitmap(bitmap)*/
        val content = "bitcoin:3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy"

        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }

        imageViewQRCode.setImageBitmap(bitmap)
        Log.i("Bitmap","$bitmap")
        return inflater.inflate(R.layout.q_r_code_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QRCodeViewModel::class.java)


    }

}