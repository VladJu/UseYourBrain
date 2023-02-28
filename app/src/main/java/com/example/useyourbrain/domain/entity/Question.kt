package com.example.useyourbrain.domain.entity

data class Question(
    val sumShowInCircle : Int,
    val visibleNumberInLeftSquare : Int,
    val optionsAnswer : List<Int>
) {

    val rightAnswer : Int
    get() = sumShowInCircle - visibleNumberInLeftSquare
}