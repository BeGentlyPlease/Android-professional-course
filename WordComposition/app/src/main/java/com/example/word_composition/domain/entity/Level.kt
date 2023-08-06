package com.example.word_composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level : Parcelable {
    Test,
    EASY,
    NORMAL,
    HARD
}