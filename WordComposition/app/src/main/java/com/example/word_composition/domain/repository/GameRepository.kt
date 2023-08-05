package com.example.word_composition.domain.repository

import com.example.word_composition.domain.entity.GameSettings
import com.example.word_composition.domain.entity.Level
import com.example.word_composition.domain.entity.Question

interface GameRepository {
    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question
    fun getGameSettings(level: Level): GameSettings
}