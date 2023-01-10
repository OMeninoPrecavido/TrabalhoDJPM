package com.example.trabalhodjpm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.trabalhodjpm.databinding.ActivityConfigBinding
import com.example.trabalhodjpm.databinding.ActivityMainBinding

class ConfigActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goBackButton.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        if(p0.id == R.id.go_back_button) {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}