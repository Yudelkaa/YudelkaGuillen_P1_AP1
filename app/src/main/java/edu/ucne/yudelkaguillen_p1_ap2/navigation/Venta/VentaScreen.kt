package edu.ucne.yudelkaguillen_p1_ap2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.yudelkaguillen_p1_ap2.ui.theme.YudelkaGuillen_P1_AP2Theme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import edu.ucne.yudelkaguillen_p1_ap2.navigation.Venta.VentaUiEvent
import edu.ucne.yudelkaguillen_p1_ap2.navigation.Venta.VentaUiState
import edu.ucne.yudelkaguillen_p1_ap2.navigation.Venta.VentaViewModel

@Composable
fun VentaScreen(
    viewModel: VentaViewModel = hiltViewModel(),
    goVentaList: () -> Unit,
    ventaId: Int,
    goBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    VentaBodyScreen(
        uiState = uiState,
        goVentaList = goVentaList,
        ventaId = ventaId,
        goBack = goBack,
        saveVenta = { viewModel.onEvent(VentaUiEvent.Save) },
        deleteVenta = { viewModel.onEvent(VentaUiEvent.Delete) },
        nuevoVenta = { viewModel.onEvent(VentaUiEvent.Nuevo) },
        onEvent = { event -> viewModel.onEvent(event) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentaBodyScreen(
    uiState: VentaUiState,
    goVentaList: () -> Unit,
    ventaId: Int,
    goBack: () -> Unit,
    saveVenta: () -> Unit,
    deleteVenta: () -> Unit,
    nuevoVenta: () -> Unit,
    onEvent: (VentaUiEvent) -> Unit
) {
    LaunchedEffect(key1 = ventaId) {
        if (ventaId > 0) {
            onEvent(VentaUiEvent.VentaSelected(ventaId))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Venta") },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        label = { Text(text = "Cliente") },
                        value = uiState.cliente ?: "",
                        onValueChange = { onEvent(VentaUiEvent.ClienteChanged(it)) }

                    )
                    OutlinedTextField(
                        label = { Text(text = "Galones") },
                        value = uiState.galones?.toString() ?: "",
                        onValueChange = {
                            onEvent(
                                VentaUiEvent.GalonesChanged(
                                    it.toFloatOrNull() ?: 0f
                                )
                            )

                        }
                    )
                    OutlinedTextField(
                        label = { Text(text = "Precio") },
                        value = uiState.precio?.toString() ?: "",
                        onValueChange = {
                            onEvent(
                                VentaUiEvent.PrecioChanged(
                                    it.toFloatOrNull() ?: 0f
                                )
                            )
                        }
                    )
                    OutlinedTextField(
                        label = { Text(text = "Descuento") },
                        value = uiState.descuento?.toString() ?: "",
                        onValueChange = {},

                        )

                    OutlinedTextField(
                        label = { Text(text = "Total") },
                        value = uiState.total?.toString() ?: "",
                        onValueChange = {
                            onEvent(VentaUiEvent.TotalChanged(it.toFloatOrNull() ?: 0f))
                        }
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    OutlinedButton(onClick = { onEvent(VentaUiEvent.CalcularDescuento) }) {
                        Text(text = "Calcular Descuento")
                    }

                    OutlinedButton(onClick = { onEvent(VentaUiEvent.CalcularTotal) }) {
                        Text(text = "Calcular Total")
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(onClick = nuevoVenta) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Nuevo"
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(text = "Nuevo")
                        }

                        OutlinedButton(onClick = {
                            saveVenta()
                            goBack()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Guardar"
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(text = "Guardar")
                        }

                        OutlinedButton(onClick = { onEvent(VentaUiEvent.Delete) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar"
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(text = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ScreenPreview() {
    YudelkaGuillen_P1_AP2Theme {
        VentaBodyScreen(
            uiState = VentaUiState(),
            goVentaList = {},
            ventaId = 0,
            goBack = {},
            saveVenta = {},
            deleteVenta = {},
            nuevoVenta = {},
            onEvent = {}
        )
    }
}