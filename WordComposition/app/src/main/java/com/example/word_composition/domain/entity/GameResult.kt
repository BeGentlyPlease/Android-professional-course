package com.example.word_composition.domain.entity

import java.io.Serializable

data class GameResult(
    val winner: Boolean,
    val countOfQuestions: Int,
    val countOfRightAnswers: Int,
    val gameSettings: GameSettings,
) : Serializable