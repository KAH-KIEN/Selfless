package com.example.eventdetails.ui.LoginUser

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.example.eventdetails.databinding.FragmentEditProfileBinding
import com.example.eventdetails.databinding.FragmentProfilePicBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class ProfilePicFragment : Fragment() {
    private var db = FirebaseDatabase.getInstance().reference
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentProfilePicBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false);
        binding = FragmentProfilePicBinding.inflate(inflater, container, false)
        val view = binding.root
        auth = Firebase.auth

        binding.buttonAddPhoto.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)

            Log.d("changePhoto", "Try to show photo selector")
        }
        binding.textViewPhotoDone.setOnClickListener {
            if(imageuri!=null){
                uploadPhotoToStorage()
            }
            Toast.makeText(getActivity(), "Changes saved!", Toast.LENGTH_SHORT).show()

            requireView().findNavController().navigate(R.id.navigation_home)
        }

        return view
    }
    var imageuri: Uri?=null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            //proceed and check what image selected
            imageuri = data.data
            binding.profileImage?.setImageURI(imageuri)

        }

    }

    private fun uploadPhotoToStorage(){
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(imageuri!!)
            .addOnSuccessListener{
                Log.d("Change photo", "Successfully uploaded image!")
                ref.downloadUrl.addOnSuccessListener{
                    it.toString()
                    Log.d("uploadPhoto","get file location")
                    var user = auth.currentUser
                    if (user != null) {
                        var userID = auth.getCurrentUser()?.uid
                        db.child("User").child("$userID").child("photoURL").setValue("$it")
                    }
                }
            }
    }
}