package com.neoslax.composition.domain.usecases

import com.neoslax.composition.domain.entity.GameSettings
import com.neoslax.composition.domain.entity.Level
import com.neoslax.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(level: Level): GameSettings {
        return gameRepository.getGameSettings(level)
    }
}