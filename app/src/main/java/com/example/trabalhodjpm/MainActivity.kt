package com.example.trabalhodjpm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.trabalhodjpm.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.configButton.setOnClickListener(this)
        binding.addButton.setOnClickListener(this)

        val id = firebaseAuth.currentUser?.uid.toString()

        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(id).get().addOnSuccessListener {

            if (it.exists()) {

                it.child("subjectList").children.forEach{

                    val shortRest = it.child("shortRest").value
                    val longRest = it.child("longRest").value
                    val studyTime = it.child("studyTime").value
                    val name = it.child("name").value.toString()

                    Controller.Companion.currUserSubjList.add(Subject("Mobile", 0, 0, 0, 0))
                    println(Controller.Companion.currUserSubjList[0].name)

                }

            } else {

                Toast.makeText(this, "Account not found.", Toast.LENGTH_SHORT).show()

            }

        }.addOnFailureListener{

            Toast.makeText(this, "Failed to reach account.", Toast.LENGTH_SHORT).show()

        }
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