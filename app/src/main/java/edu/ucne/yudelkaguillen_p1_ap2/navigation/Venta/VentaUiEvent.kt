package edu.ucne.yudelkaguillen_p1_ap2.navigation.Venta

sealed interface VentaUiEvent {
    data class ClienteChanged(val cliente: String) : VentaUiEvent
    data class GalonesChanged(val galones: Double) : VentaUiEvent
    data class TotalChanged(val total: Double): VentaUiEvent
    data class PrecioChanged(val precio: Double) : VentaUiEvent
    data class DescuentoChanged(val descuento: Double) : VentaUiEvent

    data class VentaSelected(val id: Int) : VentaUiEvent
    data object Save: VentaUiEvent
    data class Delete (val id: Int): VentaUiEvent
    data object Validate: VentaUiEvent
    data object Nuevo: VentaUiEvent

    data object CalcularDescuento : VentaUiEvent
    data object CalcularTotal : VentaUiEvent


}


