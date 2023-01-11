package com.example.trabalhodjpm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.trabalhodjpm.databinding.ActivityLoginBinding
import com.example.trabalhodjpm.databinding.SignInWindowBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(R.layout.sign_in_window, container, false)
        return rootView
    }

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database : DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        val btnCreate = view.findViewById<Button>(R.id.create_button)
        val btnCancel = view.findViewById<Button>(R.id.cancel_button)

        btnCreate.setOnClickListener {

            val email = view.findViewById<EditText>(R.id.new_login).text.toString()
            val pass = view.findViewById<EditText>(R.id.new_password).text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(){
                    if (it.isSuccessful) {

                        dismiss()
                        Toast.makeText(this.activity, "Account succesfully created.", Toast.LENGTH_SHORT).show()
                        database = FirebaseDatabase.getInstance().getReference("Users")
                        val id = firebaseAuth.currentUser?.uid.toString()
                        val user = User(id)
                        database.child(email).setValue(user)

                    } else {
                        Toast.makeText(this.activity, "1An error ocurred. Try again.", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
            } else {
                dismiss()
                Toast.makeText(this.activity, "2An error ocurred. Try again.", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}