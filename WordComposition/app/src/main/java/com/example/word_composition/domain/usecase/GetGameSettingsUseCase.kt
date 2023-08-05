package com.example.word_composition.domain.usecase

import com.example.word_composition.domain.entity.GameSettings
import com.example.word_composition.domain.entity.Level
import com.example.word_composition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {
    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}