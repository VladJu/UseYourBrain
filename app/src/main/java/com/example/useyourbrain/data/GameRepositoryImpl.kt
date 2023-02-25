package com.example.useyourbrain.data

import com.example.useyourbrain.domain.entity.GameSettings
import com.example.useyourbrain.domain.entity.Level
import com.example.useyourbrain.domain.entity.Question
import com.example.useyourbrain.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {
    private const val MIN_SUM_VALUE = 2
    private const val MIN_VISIBLE_VALUE = 1


    override fun generateQuestion(maxSumValue: Int, countOfOptionsAnswers: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_VISIBLE_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)

        val from = max(rightAnswer - countOfOptionsAnswers, MIN_VISIBLE_VALUE)
        val to= min(maxSumValue,rightAnswer + countOfOptionsAnswers)
        while (options.size < countOfOptionsAnswers){
            options.add(Random.nextInt(from,to))
        }
        return Question(sum,visibleNumber,options.toList())

    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> {
                GameSettings(
                    10,
                    4,
                    60,
                    10
                )
            }
            Level.EASY -> {
                GameSettings(
                    20,
                    6,
                    60,
                    60
                )
            }
            Level.NORMAL -> {
                GameSettings(
                    50,
                    7,
                    70,
                    45
                )
            }
            Level.HARD -> {
                GameSettings(
                    100,
                    15,
                    80,
                    25
                )
            }
        }
    }
}