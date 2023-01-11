package com.example.trabalhodjpm

data class User(
    val id : String,
    var subjectList : List<Subject> = mutableListOf(Subject("Mobile"))
) {

}
