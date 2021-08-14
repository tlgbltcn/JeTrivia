package com.tlgbltcn.jetrivia.data.model

import androidx.room.*
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
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
    val type: String, // multiple
    @Transient
    var status: Status = Status.IDLE
) {

    enum class Status {
        IDLE,
        CORRECT,
        WRONG
    }

    @ExperimentalSerializationApi
    @Serializer(forClass = Status::class)
    object LintSeveritySerializer : KSerializer<Status> {
        override val descriptor: SerialDescriptor = serialDescriptor<String>()
        override fun serialize(output: Encoder, obj: Status) {
            output.encodeString(obj.toString().lowercase())
        }

        override fun deserialize(input: Decoder): Status {
            return Status.valueOf(input.decodeString().uppercase())
        }
    }

    object Converter {

        @JvmStatic
        @TypeConverter
        fun fromList(value: List<String>) =
            Json.encodeToString(ListSerializer(String.serializer()), value)

        @JvmStatic
        @TypeConverter
        fun toList(value: String) =
            Json.decodeFromString(ListSerializer(String.serializer()), value)

        @ExperimentalSerializationApi
        @JvmStatic
        @TypeConverter
        fun fromStatus(value: Status) = Json.encodeToString(LintSeveritySerializer, value)

        @ExperimentalSerializationApi
        @JvmStatic
        @TypeConverter
        fun toStatus(value: String) = Json.decodeFromString(LintSeveritySerializer, value)
    }

    companion object {
        const val TABLE_NAME = "trivia"
    }
}