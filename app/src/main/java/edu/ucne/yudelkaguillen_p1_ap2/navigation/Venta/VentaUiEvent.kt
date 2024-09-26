package edu.ucne.yudelkaguillen_p1_ap2.navigation.Venta

sealed interface VentaUiEvent {
    data class ClienteChanged(val cliente: String) : VentaUiEvent
    data class GalonesChanged(val galones: Float) : VentaUiEvent
    data class TotalChanged(val total: Float): VentaUiEvent
    data class PrecioChanged(val precio: Float) : VentaUiEvent
    data class DescuentoChanged(val descuento: Double) : VentaUiEvent

    data class VentaSelected(val id: Int) : VentaUiEvent
    data object Save: VentaUiEvent
    data object Delete: VentaUiEvent
    data object Nuevo: VentaUiEvent

    data object CalcularDescuento : VentaUiEvent
    data object CalcularTotal : VentaUiEvent


}


