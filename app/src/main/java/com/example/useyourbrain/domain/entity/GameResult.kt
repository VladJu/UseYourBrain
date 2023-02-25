package com.example.useyourbrain.domain.entity

data class GameResult(
    //смайлик
    val winner : Boolean,
    val countOfRightAnswers : Int,
    val totalCountQuestionsOnAnsweredUser : Int,
    //из настроей получаем какое минимальное кол-во ответов должно быть
    val gameSettings : GameSettings

) {
}