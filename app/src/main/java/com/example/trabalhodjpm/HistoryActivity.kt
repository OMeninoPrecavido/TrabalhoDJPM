package com.example.trabalhodjpm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.Control
import android.view.MotionEvent
import com.example.trabalhodjpm.databinding.ActivityHistoryBinding
import com.example.trabalhodjpm.databinding.ActivityMainBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Controller.Companion.currUserSubjList.forEach {
            if (it.name == Controller.Companion.currSubject) {
                binding.noOfTomatosText.text = it.noOfTomatos.toString()
            }
        }
    }

    //Valores usados pela função onTouchEvent().
    private var x1 : Float = 0F
    private var x2 : Float = 0F

    //Evento de Swipe.
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                x1 = event.x
                return true
            }
            MotionEvent.ACTION_UP -> {
                x2 = event.x
                val deltaX = x2 - x1

                if (deltaX < 0) { //Swipe para a esquerda
                    startActivity(Intent(this,MainActivity::class.java))
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }
}