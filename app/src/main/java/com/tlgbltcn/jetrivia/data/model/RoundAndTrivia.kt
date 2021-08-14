package com.tlgbltcn.jetrivia.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class RoundAndTrivia(
    @Embedded
    val round: Round,

    @Relation(
        parentColumn = "round_id",
        entityColumn = "round_creator_id"
    )
    val triviaList: List<Trivia>
)