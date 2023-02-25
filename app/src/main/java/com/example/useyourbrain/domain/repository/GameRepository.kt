package com.example.useyourbrain.domain.repository

import com.example.useyourbrain.domain.entity.GameSettings
import com.example.useyourbrain.domain.entity.Level
import com.example.useyourbrain.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptionsAnswers: Int

    ): Question

    fun getGameSettings(level: Level): GameSettings
}