package com.example.trabalhodjpm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.trabalhodjpm.databinding.ActivityLoginBinding
import com.example.trabalhodjpm.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener(this)
        binding.signUpButton.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        if (p0.id == R.id.login_button) {
            val email = binding.loginBox.text.toString()
            val pass = binding.passwordBox.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener() {
                    if (it.isSuccessful) {
                        startActivity(Intent(this,MainActivity::class.java))
                    } else {
                        Toast.makeText(this, "There has been an error. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all fields in order to proceed.", Toast.LENGTH_SHORT).show()
            }
        }
        else if (p0.id == R.id.sign_up_button) {
            var dialog = SignInFragment()
            dialog.show(supportFragmentManager, "signInDialog")
        }
    }
}