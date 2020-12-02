package com.example.eventdetails.ui.LoginUser

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.example.eventdetails.databinding.FragmentEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class EditProfileFragment : Fragment() {
    private var db = FirebaseDatabase.getInstance().reference
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentEditProfileBinding
    private var mDateSetListener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        setHasOptionsMenu(false);
        auth = Firebase.auth

        val model= ViewModelProviders.of(requireActivity()).get(Communicator2::class.java)

        model.name.observe(viewLifecycleOwner,
            { o -> binding.editTextName.setText(o!!.toString()) })

        model.birthday.observe(viewLifecycleOwner,
            { o -> binding.editTextBirthday.setText(o!!.toString())  })

        model.contact.observe(viewLifecycleOwner,
            { o -> binding.editTextPhone.setText(o!!.toString()) })

        model.email.observe(viewLifecycleOwner,
            { o -> binding.editTextProfileEmail.setText(o!!.toString()) })


        var user = auth.currentUser
        if (user != null) {
            var userID = auth.getCurrentUser()?.uid
            db.child("User").child("$userID").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val photo = dataSnapshot.child("photoURL").getValue(String::class.java)
                    if (photo != null){
                        Picasso.get().load(photo).into(binding.profileImage)
                        Log.d("load image","loaded $photo")
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("Get data", "Failed to read value.", error.toException())
                }
            })
        }

        binding.editTextBirthday.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)
                val dialog = DatePickerDialog(
                    requireActivity(),
                    mDateSetListener,
                    year, month, day)
                dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                dialog.show()
            }
        })

        mDateSetListener = object: DatePickerDialog.OnDateSetListener {
            override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
                //Jan is 0, Dec is 11
                var months = month + 1
                val date = "$day" + "/" + "$months" + "/" + "$year"
                binding.editTextBirthday.text = Editable.Factory.getInstance().newEditable(date)
            }
        }

        binding.buttonEditProfileDone.setOnClickListener {
            var user = auth.currentUser
            if (binding.editTextName.text.toString()
                    .isEmpty() || binding.editTextBirthday.text.toString().isEmpty() || binding.editTextPhone.text.toString().isEmpty()
            )
                Toast.makeText(getActivity(), "Please do not leave blanks!", Toast.LENGTH_SHORT).show()
            else {
                if (user != null) {
                    var userID = auth.getCurrentUser()?.uid
                    db.child("User").child("$userID").child("name").setValue(binding.editTextName.text.toString())
                    db.child("User").child("$userID").child("birthday").setValue(binding.editTextBirthday.text.toString())
                    db.child("User").child("$userID").child("contact").setValue(binding.editTextPhone.text.toString())
                }

                if(imageuri!=null){
                    uploadPhotoToStorage()
                }
                Toast.makeText(getActivity(), "Changes saved!", Toast.LENGTH_SHORT).show()

                requireView().findNavController().navigate(R.id.navigation_profile)
            }

        }

        binding.buttonChangePhoto.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)

            Log.d("changePhoto", "Try to show photo selector")
        }


        return view
    }

    var imageuri: Uri?=null

    @RequiresApi(Build.VERSION_CODES.P)
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