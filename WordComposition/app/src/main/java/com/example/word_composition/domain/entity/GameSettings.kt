package com.example.word_composition.domain.entity

data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightQuestions: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int,
)