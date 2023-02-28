package com.example.useyourbrain.domain.usecases

import com.example.useyourbrain.domain.entity.Question
import com.example.useyourbrain.domain.repository.GameRepository

//generate question
class GenerateQuestionUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS_ANSWERS)
    }

    private companion object {

        private const val COUNT_OF_OPTIONS_ANSWERS = 6

    }
}