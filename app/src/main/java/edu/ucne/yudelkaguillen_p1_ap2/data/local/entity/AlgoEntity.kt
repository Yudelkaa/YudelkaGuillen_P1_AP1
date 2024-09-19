package edu.ucne.yudelkaguillen_p1_ap2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Algos")
data class AlgoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
    )