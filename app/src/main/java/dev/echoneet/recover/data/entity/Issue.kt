package dev.echoneet.recover.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class Issue (
    @NotNull
    @PrimaryKey
    val id: Int,
    val code: String,
    val title: String,
    val description: String,
    val status: String
)