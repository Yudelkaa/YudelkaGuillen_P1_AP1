package edu.ucne.yudelkaguillen_p1_ap2.navigation.Venta

import edu.ucne.yudelkaguillen_p1_ap2.data.local.entities.VentaEntity

data class VentaUiState(
    val ventaId: Int? = 0,
    val errorMessage: String? = null,
    val cliente: String? = null,
    val galones:Double? = null,
    val precio: Double? = null,
    val descuento: Double? = null,
    val total: Double? = null,
    val listaVenta: List<VentaEntity> = emptyList(),
    val isSuccess: Boolean = false
)


