package com.tlgbltcn.jetrivia.data.model

import androidx.room.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

@Serializable
@Entity(
    tableName = Trivia.TABLE_NAME, foreignKeys = [
        ForeignKey(
            entity = Round::class,
            parentColumns = ["round_id"],
            childColumns = ["round_creator_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ], indices = [Index("round_creator_id")]
)
data class Trivia(
    @Transient
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "trivia_id")
    var triviaId: Long? = null,
    @Transient
    @ColumnInfo(name = "round_creator_id")
    val roundCreatorId: Long? = null,
    @SerialName("category")
    val category: String, // Entertainment: Cartoon & Animations
    @SerialName("correct_answer")
    val correctAnswer: String, // Marsh
    @SerialName("difficulty")
    val difficulty: String, // easy
    @SerialName("incorrect_answers")
    val incorrectAnswers: List<String>,
    @SerialName("question")
    val question: String, // In South Park, what is Stan&#039;s surname?
    @SerialName("type")
    val type: String // multiple
) {

    object Converter {

        @JvmStatic
        @TypeConverter
        fun fromList(value: List<String>) =
            Json.encodeToString(ListSerializer(String.serializer()), value)

        @JvmStatic
        @TypeConverter
        fun toList(value: String) =
            Json.decodeFromString(ListSerializer(String.serializer()), value)
    }

    companion object {
        const val TABLE_NAME = "trivia"
    }
}