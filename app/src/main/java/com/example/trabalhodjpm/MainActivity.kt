package com.example.trabalhodjpm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.trabalhodjpm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.configButton.setOnClickListener(this)
        binding.addButton.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        if (p0.id == R.id.config_button) {
            startActivity(Intent(this,ConfigActivity::class.java))
        }
        else if (p0.id == R.id.add_button) {
            var dialog = AddSubjectFragment()
            dialog.show(supportFragmentManager, "customDialog")
        }
    }

    //Valores usados pela função onTouchEvent()
    private var x1 : Float = 0F
    private var x2 : Float = 0F

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                x1 = event.x
                return true
            }
            MotionEvent.ACTION_UP -> {
                x2 = event.x
                val deltaX = x2 - x1

                if (deltaX > 0) { //Swipe para a direita
                    startActivity(Intent(this,HistoryActivity::class.java))
                }

                else if (deltaX < 0) { //Swipe para a esquerda
                    startActivity(Intent(this,ProgrammingActivity::class.java))
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }
}