package com.example.useyourbrain.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswersForWinner: Int,
    val minPercentOfRightAnswersForWinner: Int,
    val gameTimeInSeconds: Int

) : Parcelable