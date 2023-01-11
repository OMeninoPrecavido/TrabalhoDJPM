package com.example.trabalhodjpm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddSubjectFragment : DialogFragment() {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var database : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(R.layout.add_subject_window, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        val btnCreate = view.findViewById<Button>(R.id.create_button)
        val btnCancel = view.findViewById<Button>(R.id.cancel_button)

        btnCreate.setOnClickListener {
            val shortRest = 5
            val longRest = 15
            val studyTime = 25
            val name = view.findViewById<EditText>(R.id.new_subject_name).text

            database = FirebaseDatabase.getInstance().getReference("Users")

            Controller.Companion.currUserSubjList.add(Subject(name.toString(), shortRest, longRest, studyTime, 0))

            val id = firebaseAuth.currentUser?.uid.toString()

            database.child(id).child("subjectList").setValue(Controller.Companion.currUserSubjList)
            
            dismiss()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }

}