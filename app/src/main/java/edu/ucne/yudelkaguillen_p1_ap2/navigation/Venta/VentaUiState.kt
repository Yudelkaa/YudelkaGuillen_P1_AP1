package edu.ucne.yudelkaguillen_p1_ap2.navigation.Venta

import edu.ucne.yudelkaguillen_p1_ap2.data.local.entities.VentaEntity

data class VentaUiState(
    val ventaId: Int? = null,
    val cliente: String = "",
    val galones:Double? = 0.0,
    val precio: Double? = 0.0,
    val descuento: Double? = 0.0,
    val totalDescuento : Double? = 0.0,
    val total: Double? = 0.0,
    val listaVenta: List<VentaEntity> = emptyList(),

    val errorMessage: String? = "",
    val message: String? = "",
    val messageCliente: String? = "",
    val messageGalones: String? = "",
    val messagePrecio: String? = "",
    val messageDescuento: String? = "",
    val messageTotal: String? = "",
    val messageTotalDescuento: String? = "",
    val isSuccess: Boolean = false
)


