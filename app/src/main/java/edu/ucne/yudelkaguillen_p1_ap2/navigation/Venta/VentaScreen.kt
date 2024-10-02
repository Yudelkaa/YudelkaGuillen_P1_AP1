package edu.ucne.yudelkaguillen_p1_ap2.navigation.Venta

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

@Composable
fun VentaScreen(
    viewModel: VentaViewModel = hiltViewModel(),
    goVentaList: () -> Unit,
    ventaId: Int,
    goBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        if (ventaId > 0) {
            viewModel.onEvent(VentaUiEvent.VentaSelected(ventaId))
        }
    }
    VentaBodyScreen(
        uiState = uiState,
        goVentaList = goVentaList,
        goBack = goBack,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentaBodyScreen(
    uiState: VentaUiState,
    goVentaList: () -> Unit,
    goBack: () -> Unit,
    onEvent: (VentaUiEvent) -> Unit
) {
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
                        .padding(4.dp)
                ) {
                    OutlinedTextField(
                        label = { Text(text = "Cliente") },
                        value = uiState.cliente,
                        onValueChange = { onEvent(VentaUiEvent.ClienteChanged(it)) },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.AccountBox,
                                contentDescription = "Campo Cliente"
                            )
                        }
                    )

                    if (uiState.messageCliente != " ") {
                        uiState.messageCliente?.let {
                            Text(
                                text = it,
                                color = Color.Red,
                                fontStyle = FontStyle.Italic,
                                fontSize = 14.sp
                            )
                        }
                    }


                    OutlinedTextField(
                        label = { Text(text = "Galones") },
                        value = uiState.galones.toString().replace("null", "0.0"),
                        onValueChange = {
                            onEvent(
                                VentaUiEvent.GalonesChanged(
                                    it.toDoubleOrNull() ?: 0.0
                                )
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),

                    )

                    if (uiState.messageGalones != "") {
                        uiState.messageGalones?.let {
                            Text(
                                text = it,
                                color = Color.Red,
                                fontStyle = FontStyle.Italic,
                                fontSize = 14.sp
                            )
                        }
                    }

                    OutlinedTextField(
                        label = { Text(text = "Precio") },
                        value = uiState.precio.toString().replace("null", "0.0"),
                        onValueChange = {
                            onEvent(
                                VentaUiEvent.PrecioChanged(
                                    it.toDoubleOrNull() ?: 0.0
                                )
                            )
                        },
                        placeholder = { Text(text = "0.0") },
                        prefix = { Text(text = "$") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        )

                    if (uiState.messagePrecio != "") {
                        uiState.messagePrecio?.let {
                            Text(
                                text = it,
                                color = Color.Red,
                                fontStyle = FontStyle.Italic,
                                fontSize = 14.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        label = { Text(text = "Descuento") },
                        value = uiState.descuento.toString().replace("null", "0.0"),
                        onValueChange = {
                            onEvent(
                                VentaUiEvent.DescuentoChanged(
                                    it.toDoubleOrNull() ?: 0.0
                                )
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    )
                    if (uiState.messageDescuento != "") {
                        uiState.messageDescuento?.let {
                            Text(
                                text = it,
                                color = Color.Red,
                                fontStyle = FontStyle.Italic,
                                fontSize = 14.sp
                            )
                        }
                    }

                    OutlinedTextField(
                        label = { Text(text = "Total") },
                        value = uiState.total.toString().replace("null", "0.0"),
                        onValueChange = {
                            onEvent(
                                VentaUiEvent.TotalChanged(
                                    it.toDoubleOrNull() ?: 0.0
                                )
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = {
                                onEvent(VentaUiEvent.Nuevo)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Nuevo"
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(text = "Nuevo")
                        }

                        OutlinedButton(onClick = {
                            onEvent(VentaUiEvent.Save)
                            if(uiState.isSuccess){
                                goBack()
                            }

                        }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Guardar"
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(text = "Guardar")

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
            goBack = {},
            onEvent = {}
        )
    }
}
