package com.neoslax.composition.domain.usecases

import com.neoslax.composition.domain.entity.GameSettings
import com.neoslax.composition.domain.entity.Question
import com.neoslax.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val repository: GameRepository
) {
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OBJECTS)
    }

    private companion object{
        const val COUNT_OF_OBJECTS = 4
    }
}