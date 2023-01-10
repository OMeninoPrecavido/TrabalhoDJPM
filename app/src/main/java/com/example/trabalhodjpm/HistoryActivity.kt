package com.example.trabalhodjpm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
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

                if (deltaX < 0) { //Swipe para a esquerda
                    startActivity(Intent(this,MainActivity::class.java))
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }
}