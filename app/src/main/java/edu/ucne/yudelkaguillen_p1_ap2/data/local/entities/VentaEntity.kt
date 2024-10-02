package edu.ucne.yudelkaguillen_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Algos")
data class VentaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val cliente: String? = null,
    val galones: Double? = null,
    val precio: Double? = null,
    val descuento: Double? = null,
    val total: Double? = null,
    val totalDescuento: Double? = null

    )