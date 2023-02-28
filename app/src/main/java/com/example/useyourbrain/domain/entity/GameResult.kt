package com.example.useyourbrain.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Parcelize
data class GameResult (
    //смайлик
    val winner : Boolean,
    val countOfRightAnswers : Int,
    val totalCountQuestions : Int,
    //из настроей получаем какое минимальное кол-во ответов должно быть
    val gameSettings : GameSettings

) : Parcelable