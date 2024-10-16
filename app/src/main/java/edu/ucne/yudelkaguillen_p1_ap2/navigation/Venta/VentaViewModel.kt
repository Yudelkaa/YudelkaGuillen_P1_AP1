package edu.ucne.yudelkaguillen_p1_ap2.navigation.Venta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.yudelkaguillen_p1_ap2.data.remote.dto.VentaDto
import edu.ucne.yudelkaguillen_p1_ap2.data.repository.Resource
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import edu.ucne.yudelkaguillen_p1_ap2.data.repository.VentaRepository
import kotlinx.coroutines.flow.collectLatest
import java.text.DecimalFormat

@HiltViewModel
class VentaViewModel @Inject constructor(
    private val ventaRepository: VentaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(VentaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllVentas()
        }
    }

    private suspend fun getAllVentas() {
        ventaRepository.getVentas().collectLatest { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            listaVenta = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            errorMessage = result.message ?: "Error desconocido",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: VentaUiEvent) {
        when (event) {

            is VentaUiEvent.Validate -> validateInput()
            is VentaUiEvent.VentaSelected -> EditarVenta(event.id)
            is VentaUiEvent.ClienteChanged -> ClienteChanged(event.cliente)
            is VentaUiEvent.GalonesChanged -> GalonesChanged(event.galones)
            is VentaUiEvent.PrecioChanged -> PrecioChanged(event.precio)
            is VentaUiEvent.DescuentoChanged -> DescuentoChanged(event.descuento)
            is VentaUiEvent.TotalChanged -> calcularTotal()
            is VentaUiEvent.Delete -> delete(event.id)
            is VentaUiEvent.CalcularDescuento -> calcularDescuento()
            is VentaUiEvent.CalcularTotal -> calcularTotal()


            VentaUiEvent.CalcularDescuento -> calcularDescuento()
            VentaUiEvent.CalcularTotal -> calcularTotal()
            VentaUiEvent.Save -> saveVenta()
            VentaUiEvent.Nuevo -> Nuevo()
        }
    }

    private fun saveVenta() {
        if (validateInput()) {
            viewModelScope.launch {
                try {
                    if (uiState.value.ventaId != null) {
                        ventaRepository.updateVentas(uiState.value.toEntity())
                    } else {
                        ventaRepository.addVentas(uiState.value.toEntity())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    private fun delete(id: Int) {
        viewModelScope.launch {
            val venta = ventaRepository.findVentas(id)
            if (venta != null) {
                ventaRepository.deleteVentas(id)
                _uiState.update {
                    it.copy(
                        listaVenta = it.listaVenta.filter { v -> v.id != id },
                        isSuccess = true
                    )
                }
                getAllVentas()
            }
        }
    }

    private fun calcularDescuento() {
        val galones = _uiState.value.galones ?: 0.0
        val precio = _uiState.value.precio ?: 0.0
        val descuento = (galones * precio) * 0.1
        _uiState.update { it.copy(descuento = descuento) }
    }

    private fun calcularTotal() {
        val galones = _uiState.value.galones ?: 0.0
        val precio = _uiState.value.precio ?: 0.0
        val descuento = _uiState.value.descuento ?: 0.0
        val total = (galones * precio) - descuento
        _uiState.update { it.copy(total = total) }
    }

    private fun Nuevo() {
        _uiState.update {
            it.copy(
                cliente = "",
                galones = 0.0,
                precio = 0.0,
                descuento = 0.0,
                errorMessage = "",
                total = 0.0,

                )
        }
    }

    private fun EditarVenta(id: Int) {
        viewModelScope.launch {
            val venta = ventaRepository.findVentas(id)
            if (id > 0) {
                _uiState.update {
                    it.copy(
                        ventaId = venta?.id,
                        cliente = venta?.cliente ?: "",
                        galones = venta?.galones,
                        precio = venta?.precio ?: 0.0,
                        descuento = venta?.descuento ?: 0.0,
                        total = venta?.total ?: 0.0
                    )
                }
            }
        }
    }


    fun ClienteChanged(cliente: String) {
        _uiState.update {
            it.copy(
                cliente = cliente,
                messageCliente = if (cliente.isNotBlank()) null else "No puede estar vacío"
            )

        }
    }

    fun GalonesChanged(galones: Double) {
        _uiState.update {
            it.copy(
                galones = galones,
                messageGalones = if (galones > 0) null else "No puede estar vacío"
            )
        }
        TotalDescuentoChange()
        TotalChange()
    }

    fun DescuentoChanged(descuento: Double) {
        _uiState.update {
            it.copy(
                descuento = descuento,
                messageDescuento = if (descuento > 0) null else "No puede estar vacío"

            )
        }
        TotalDescuentoChange()
        TotalChange()
    }

    fun PrecioChanged(precio: Double) {
        val newPrecio = precio.toDouble()
        val errorMessage = when {
            newPrecio <= 0.0 -> "El precio debe ser mayor que cero."
            else -> null
        }

        _uiState.update { currentState ->
            currentState.copy(
                precio = newPrecio,
                messagePrecio = errorMessage
            )
        }
    }


    private fun TotalDescuentoChange() {
        val totalDescuento = (uiState.value.galones ?: 0.0) * (uiState.value.descuento ?: 0.0)
        _uiState.update {
            it.copy(
                totalDescuento = totalDescuento,
            )
        }
    }

    private fun validateInput(): Boolean {
        val ventas = _uiState.value.listaVenta

        return when {
            _uiState.value.cliente.isNullOrBlank() -> {
                _uiState.update { it.copy(errorMessage = "El campo cliente no puede ir vacío") }
                false
            }

            ventas.any { it.cliente == _uiState.value.cliente } -> {
                _uiState.update { it.copy(errorMessage = "Ya existe un cliente con este nombre") }
                false
            }

            (_uiState.value.galones ?: 0.0) <= 0.0 -> {
                _uiState.update { it.copy(errorMessage = "El campo galones debe ser mayor a 0.0") }
                false
            }

            (_uiState.value.precio ?: 0.0) <= 0.0 -> {
                _uiState.update { it.copy(errorMessage = "El campo precio debe ser mayor a 0.0") }
                false
            }

            (_uiState.value.descuento ?: 0.0) > (_uiState.value.precio ?: 0.0) -> {
                _uiState.update { it.copy(errorMessage = "El descuento debe ser menor que el precio") }
                false
            }

            else -> true
        }
    }


    fun TotalChange() {
        val total = (uiState.value.galones ?: 0.0) * (uiState.value.precio
            ?: 0.0) - (uiState.value.descuento ?: 0.0)
        val df = DecimalFormat("#.00")
        val totalFormateado = df.format(total)
        _uiState.update {
            it.copy(
                total = totalFormateado.toDouble(),
                messageTotal = if (totalFormateado.toDouble() > 0.0) null else "Total no puede estar vacío"
            )
        }
    }


    fun VentaUiState.toEntity() = VentaDto(
        cliente = cliente ?: "",
        galones = galones ?: 0.0,
        precio = precio ?: 0.0,
        descuento = descuento ?: 0.0,
        total = total ?: 0.0,
        id = ventaId ?: 0,
        totalDescuento = totalDescuento ?: 0.0,

    )
}
