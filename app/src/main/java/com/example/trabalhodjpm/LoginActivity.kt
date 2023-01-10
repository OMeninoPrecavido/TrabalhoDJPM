package com.example.trabalhodjpm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.trabalhodjpm.databinding.ActivityLoginBinding
import com.example.trabalhodjpm.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener(this)
        binding.signUpButton.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        if (p0.id == R.id.login_button) {
            startActivity(Intent(this,MainActivity::class.java))
        }
        else if (p0.id == R.id.sign_up_button) {
            var dialog = SignInFragment()
            dialog.show(supportFragmentManager, "customDialog")
        }
    }
}