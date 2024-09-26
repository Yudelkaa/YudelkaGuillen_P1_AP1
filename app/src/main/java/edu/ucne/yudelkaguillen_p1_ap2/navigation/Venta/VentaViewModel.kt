package edu.ucne.yudelkaguillen_p1_ap2.navigation.Venta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.yudelkaguillen_p1_ap2.data.local.entities.VentaEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import edu.ucne.yudelkaguillen_p1_ap2.data.repository.VentaRepository

@HiltViewModel
class VentaViewModel @Inject constructor(
    private val ventaRepository: VentaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(VentaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAllVentas()
    }

    private fun getAllVentas() {
        viewModelScope.launch {
            ventaRepository.getAll().collect { listaVenta ->
                _uiState.update { it.copy(listaVenta = listaVenta) }
            }
        }
    }

    private fun delete() {
        viewModelScope.launch {
            ventaRepository.delete(_uiState.value.toEntity())
        }
    }

    private fun editarVenta(id: Int) {
        viewModelScope.launch {
            val venta = ventaRepository.find(id)
            if (id > 0) {
                _uiState.update {
                    it.copy(
                        cliente = venta?.cliente ?: "",
                        galones = venta?.galones ,
                        precio = venta?.precio ?: 0.0,
                        descuento = venta?.descuento ?: 0.0,
                        total = venta?.total ?: 0.0
                    )
                }
            }
        }
    }

    fun onEvent(event: VentaUiEvent) {
        when (event) {
            is VentaUiEvent.ClienteChanged -> {
                _uiState.update { it.copy(cliente = event.cliente) }
            }
            is VentaUiEvent.GalonesChanged -> {
                _uiState.update { it.copy(galones = event.galones.toDouble()) }
            }
            is VentaUiEvent.PrecioChanged -> {
                _uiState.update { it.copy(precio = event.precio.toDouble()) }
            }
            is VentaUiEvent.DescuentoChanged -> {
                _uiState.update { it.copy(descuento = event.descuento) }
            }
            is VentaUiEvent.TotalChanged -> {
                _uiState.update { it.copy(total = event.total.toDouble()) }
            }
            VentaUiEvent.Nuevo -> {
                _uiState.update {
                    it.copy(
                        cliente = "",
                        galones = 0.0,
                        precio = 0.0,
                        descuento = 0.0,
                        total = 0.0,
                        errorMessage = null,
                        isSuccess = false
                    )
                }
            }
            VentaUiEvent.Delete -> delete()
            is VentaUiEvent.VentaSelected -> editarVenta(event.id)
            VentaUiEvent.Save -> saveVenta()
            VentaUiEvent.CalcularDescuento -> calcularDescuento()
            VentaUiEvent.CalcularTotal -> calcularTotal()
        }
    }


    private fun saveVenta() {
        if (validateInput()) {
            viewModelScope.launch {
                ventaRepository.save(_uiState.value.toEntity())
                _uiState.update {
                    it.copy(
                        isSuccess = true,
                        errorMessage = null
                    )
                }
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

    private fun validateInput(): Boolean {
        return when {
            _uiState.value.cliente.isNullOrBlank() -> {
                _uiState.update { it.copy(errorMessage = "El campo cliente no puede ir vacío") }
                false
            }
            _uiState.value.galones == null -> {
                _uiState.update { it.copy(errorMessage = "El campo galones no puede ir vacío") }
                false
            }
            _uiState.value.precio == null -> {
                _uiState.update { it.copy(errorMessage = "El campo precio no puede ir vacío") }
                false
            }
            _uiState.value.descuento == null -> {
                _uiState.update { it.copy(errorMessage = "El campo descuento no puede ir vacío") }
                false
            }
            _uiState.value.total == null -> {
                _uiState.update { it.copy(errorMessage = "El campo total no puede ir vacío") }
                false
            }
            else -> true
        }
    }
}

fun VentaUiState.toEntity() = VentaEntity(
    cliente = cliente ?: "",
    galones = galones ?: 0.0,
    precio = precio ?: 0.0,
    descuento = descuento ?: 0.0,
    total = total ?: 0.0
)











