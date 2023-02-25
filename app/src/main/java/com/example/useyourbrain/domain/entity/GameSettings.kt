package com.example.useyourbrain.domain.entity

data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswersForWinner: Int,
    val minPercentOfRightAnswersForWinner: Int,
    val gameTimeInSeconds: Int

) {
}