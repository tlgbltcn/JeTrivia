package com.tlgbltcn.jetrivia.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Round(
    @SerialName("response_code")
    val responseCode: Int?, // 0
    @SerialName("results")
    val trivias: List<Trivia>?
)