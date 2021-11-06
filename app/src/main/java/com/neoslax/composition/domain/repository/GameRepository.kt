package com.neoslax.composition.domain.repository

import com.neoslax.composition.domain.entity.GameSettings
import com.neoslax.composition.domain.entity.Level
import com.neoslax.composition.domain.entity.Question

interface GameRepository {
    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question
    fun getGameSettings(level: Level): GameSettings
}