package com.example.trabalhodjpm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import com.example.trabalhodjpm.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database : DatabaseReference

    //Inicializar variaveis do timer
    lateinit var timer: CountDownTimer
    var time = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.configButton.setOnClickListener(this)
        binding.addButton.setOnClickListener(this)
        binding.buttonStart.setOnClickListener(this)
        binding.buttonPause.setOnClickListener(this)
        binding.buttonEnd.setOnClickListener(this)


        val id = firebaseAuth.currentUser?.uid.toString()

        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(id).get().addOnSuccessListener {

            if (it.exists()) {

                //Limpa-se as listas para que não aumentem sempre que se entrar na Main Activity.
                Controller.Companion.currUserSubjList.clear()
                Controller.Companion.dropDownArray.clear()

                //Pega o que está na database e adiciona à lista em Controller.
                it.child("subjectList").children.forEach{

                    val shortRest = it.child("shortRest").value.toString().toInt()
                    val longRest = it.child("longRest").value.toString().toInt()
                    val studyTime = it.child("studyTime").value.toString().toInt()
                    val name = it.child("name").value.toString()

                    Controller.Companion.currUserSubjList.add(Subject(name, shortRest, longRest, studyTime, 0))
                    Controller.Companion.dropDownArray.add(name)
                }


            } else {

                Toast.makeText(this, "Account not found.", Toast.LENGTH_SHORT).show()

            }

        }.addOnFailureListener{

            Toast.makeText(this, "Failed to reach account.", Toast.LENGTH_SHORT).show()

        }

        //Código para o funcionamento do dropdown menu.
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Controller.Companion.dropDownArray)
        binding.dropdownMenu.adapter = arrayAdapter
        binding.dropdownMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Controller.Companion.currSubject = binding.dropdownMenu.selectedItem.toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.subject.text = Controller.Companion.currSubject

        //Processos do timer
        time = ObtainTime()
        timer = object : CountDownTimer(time.toLong(),  1_000){
            override fun onTick(remaining: Long) {
                var tempoFormated = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(remaining), TimeUnit.MILLISECONDS.toSeconds(remaining) % TimeUnit.MINUTES.toSeconds(1))
                binding.timer.text = tempoFormated
            }

            override fun onFinish() {
                binding.timer.text = "Done!"
            }

        }

    }

    private fun ObtainTime(): Int {
        var tempo = 0

        Controller.Companion.currUserSubjList.forEach{
            if(it.name == Controller.Companion.currSubject){
                tempo = it.studyTime
            }
        }
        tempo = TimeUnit.MINUTES.toMillis(tempo.toLong()).toInt()

        return tempo
    }



    override fun onClick(p0: View) {
        if (p0.id == R.id.config_button) {
            startActivity(Intent(this,ConfigActivity::class.java))
        }
        else if (p0.id == R.id.add_button) {
            var dialog = AddSubjectFragment()
            dialog.show(supportFragmentManager, "customDialog")
        }else if(p0.id == R.id.button_start){
            time = ObtainTime()
            timer.start()
        }else if(p0.id == R.id.button_end){
            timer.cancel()
            
        }else if(p0.id == R.id.button_pause){

        }
    }

    //Valores usados pela função onTouchEvent()
    private var x1 : Float = 0F
    private var x2 : Float = 0F

    //Código da navegação por swipe
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