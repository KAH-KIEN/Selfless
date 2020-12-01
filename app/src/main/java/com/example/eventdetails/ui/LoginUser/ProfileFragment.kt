package com.example.eventdetails.ui.LoginUser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.eventdetails.R
import com.example.eventdetails.databinding.FragmentEditProfileBinding
import com.example.eventdetails.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class ProfileFragment : Fragment() {
    private var db = FirebaseDatabase.getInstance().reference
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = Firebase.auth

        binding.buttonEditProfile.setOnClickListener {
            requireView().findNavController().navigate(R.id.navigation_editProfile)
        }

        binding.buttonSignout.setOnClickListener{
            val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_dialog_s_o,null);
            val mBuilder = AlertDialog.Builder(requireContext())
                .setView(mDialogView)
                .setTitle("Sign out")

            val mAlertDialog = mBuilder.show()
            var confirm:Boolean = false
            var textViewConfirmSO:TextView = mDialogView.findViewById(R.id.textViewConfirmSO)
            var textViewCancel:TextView = mDialogView.findViewById(R.id.textViewCancel)
            textViewConfirmSO.setOnClickListener{
                mAlertDialog.dismiss()
                auth.signOut()
                Toast.makeText(getActivity(), "User signed out", Toast.LENGTH_SHORT).show()
                requireParentFragment().findNavController().navigate(R.id.navigation_login)
            }

            textViewCancel.setOnClickListener{
                mAlertDialog.dismiss()
            }

        }

        val user = auth.currentUser
        val model= ViewModelProviders.of(requireActivity()).get(Communicator2::class.java)
        if (user != null) {
            var userID = auth.getCurrentUser()?.uid

            db.child("User").child("$userID").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {


                    val name = dataSnapshot.child("name").getValue(String::class.java)
                    val birthday = dataSnapshot.child("birthday").getValue(String::class.java)
                    val contact = dataSnapshot.child("contact").getValue(String::class.java)
                    val email = dataSnapshot.child("email").getValue(String::class.java)
                    val photo = dataSnapshot.child("photoURL").getValue(String::class.java)

                    if (photo != null){
                        Picasso.get().load(photo).into(binding.profileImage)
                        Log.d("load image","loaded $photo")
                    }
                    binding.textViewName.text = name
                    binding.textViewBirthday.text = birthday
                    binding.textViewContact.text = contact
                    binding.textViewEmail.text = email


                    model!!.setMsgCommunicator(binding.textViewName.text.toString(),binding.textViewBirthday.text.toString(),binding.textViewContact.text.toString(),binding.textViewEmail.text.toString())

                    Log.d("Get data", "Get value")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("Get data", "Failed to read value.", error.toException())
                }
            })
        }


        setHasOptionsMenu(true)
        return view
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if(user == null){
            Toast.makeText(getActivity(), "User not logged in", Toast.LENGTH_SHORT).show()
            requireView().findNavController().navigate(R.id.navigation_login)
        }
    }

}