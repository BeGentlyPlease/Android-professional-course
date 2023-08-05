package com.example.word_composition.domain.entity

data class Question(
    val sumValue: Int,
    val visibleNumber: Int,
    val options: List<Int>
)