package com.tlgbltcn.jetrivia.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Trivia(
    @SerialName("category")
    val category: String?, // Entertainment: Cartoon & Animations
    @SerialName("correct_answer")
    val correctAnswer: String?, // Marsh
    @SerialName("difficulty")
    val difficulty: String?, // easy
    @SerialName("incorrect_answers")
    val incorrectAnswers: List<String>?,
    @SerialName("question")
    val question: String?, // In South Park, what is Stan&#039;s surname?
    @SerialName("type")
    val type: String? // multiple
)