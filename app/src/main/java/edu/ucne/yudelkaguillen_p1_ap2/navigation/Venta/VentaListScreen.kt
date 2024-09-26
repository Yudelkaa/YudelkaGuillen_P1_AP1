package edu.ucne.yudelkaguillen_p1_ap2.navigation.Venta

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.yudelkaguillen_p1_ap2.data.local.entities.VentaEntity
import edu.ucne.yudelkaguillen_p1_ap2.ui.theme.YudelkaGuillen_P1_AP2Theme
import androidx.compose.foundation.lazy.items


@Composable
fun VentaListScreen (
    viewModel: VentaViewModel = hiltViewModel(),
    createVenta: () -> Unit,
    goToVenta: (Int) -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    VentaBodyListScreen(
        uiState = uiState,
        goToVenta = goToVenta,
        createVenta = createVenta
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentaBodyListScreen(
    uiState: VentaUiState,
    goToVenta: (Int) -> Unit,
    createVenta: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de Ventas") },
                actions = {
                    IconButton(onClick = createVenta) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar nuevo"
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
            if (uiState.listaVenta.isNotEmpty()) {
                VentaList(
                    ventaList = uiState.listaVenta,
                    onItemClick = goToVenta
                )
            } else {
                Text(text = "No hay elementos en la lista.")
            }
        }
    }
}

@Composable
fun VentaListItem(
    venta: VentaEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = venta.cliente ?: "Sin cliente")
            }

        }
    }
}

@Composable
fun VentaList(
    ventaList: List<VentaEntity>,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(ventaList) { venta ->
            VentaListItem(
                onClick = { venta.id?.let { onItemClick(it) } },
                venta = venta
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun ListScreenPreview() {
    YudelkaGuillen_P1_AP2Theme {
        VentaBodyListScreen(
            uiState = VentaUiState(),
            goToVenta = {},
            createVenta = {}
        )
    }
}
