package com.tlgbltcn.jetrivia.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Entity(tableName = Round.TABLE_NAME)
data class Round(
    @Transient
    @ColumnInfo(name = "round_id")
    @PrimaryKey(autoGenerate = true)
    var roundId: Long? = null,
    @Ignore
    @SerialName("results")
    val trivias: List<Trivia>? = listOf(),
    @Transient
    var isCompleted: Boolean = false,
) {

    companion object {
        const val TABLE_NAME = "trivia"
    }
}