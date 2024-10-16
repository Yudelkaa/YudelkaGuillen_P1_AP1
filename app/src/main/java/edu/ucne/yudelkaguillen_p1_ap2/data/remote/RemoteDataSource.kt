package edu.ucne.yudelkaguillen_p1_ap2.data.remote

import edu.ucne.yudelkaguillen_p1_ap2.data.remote.dto.VentaDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: VentasApi
) {
    suspend fun getVenta(id: Int) = api.getVenta(id)
    suspend fun getAll() = api.getVentas()
    suspend fun addVentas(venta: VentaDto) = api.addVenta(venta)
    suspend fun delete(id: Int) = api.deleteVenta(id)
    suspend fun update(venta: VentaDto) = api.updateVenta(venta)
    suspend fun find(id: Int) = api.getVenta(id)


}