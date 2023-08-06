package com.example.word_composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightQuestions: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int,
) : Parcelable