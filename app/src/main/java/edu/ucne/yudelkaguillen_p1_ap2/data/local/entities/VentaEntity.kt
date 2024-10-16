package edu.ucne.yudelkaguillen_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Algos")
data class VentaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val cliente: String = "",
    val galones: Double? = 0.0,
    val precio: Double? = 0.0,
    val descuento: Double? = null,
    val total: Double? = null,
    val totalDescuento: Double? = null

    )