package com.tlgbltcn.jetrivia.data.model

import androidx.room.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

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
    @Transient
    var jokers: Map<String, Boolean> = mutableMapOf(
        FIFTY_FIFTY to true,
        SKIP to true,
        EXTRA_TIME to true
    )
) {

    object Converter {

        @JvmStatic
        @TypeConverter
        fun toMap(value: String): Map<String, Boolean> =
            Json.decodeFromString(
                MapSerializer(String.serializer(), Boolean.serializer()), value
            )


        @JvmStatic
        @TypeConverter
        fun fromMap(value: Map<String, Boolean>): String =
            Json.encodeToString(
                MapSerializer(
                    String.serializer(),
                    Boolean.serializer()
                ), value
            )
    }

    companion object {
        const val TABLE_NAME = "round"
        const val FIFTY_FIFTY = "fifty_fifty"
        const val SKIP = "skip"
        const val EXTRA_TIME = "extra_time"
    }
}