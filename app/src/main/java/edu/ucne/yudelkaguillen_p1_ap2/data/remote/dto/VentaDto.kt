package edu.ucne.yudelkaguillen_p1_ap2.data.remote.dto

data class VentaDto (
    val id: Int,
    val cliente: String,
    val galones: Double,
    val precio: Double,
    val descuento: Double,
    val total: Double,
    val totalDescuento: Double
)

