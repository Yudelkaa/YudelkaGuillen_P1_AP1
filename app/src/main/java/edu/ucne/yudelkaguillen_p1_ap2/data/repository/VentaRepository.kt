package edu.ucne.yudelkaguillen_p1_ap2.data.repository

import android.util.Log
import edu.ucne.yudelkaguillen_p1_ap2.data.local.dao.VentaDao
import edu.ucne.yudelkaguillen_p1_ap2.data.local.entities.VentaEntity
import edu.ucne.yudelkaguillen_p1_ap2.data.remote.RemoteDataSource
import edu.ucne.yudelkaguillen_p1_ap2.data.remote.dto.VentaDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class VentaRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val ventaDao: VentaDao
) {
    fun getVentas(): Flow<Resource<List<VentaEntity>>> = flow {
        try {
            emit(Resource.Loading())
            val ventasRemotas = remoteDataSource.getAll()

            ventasRemotas.forEach {
                ventaDao.save(it.toVentaEntity())
            }

            ventaDao.getAll().collect { ventasLocales ->
                emit(Resource.Success(ventasLocales))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Error de internet: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error desconocido: ${e.message}"))
            ventaDao.getAll().collect { ventasLocales ->
                emit(Resource.Success(ventasLocales))
            }
        }
    }


    suspend fun getVenta(id: Int): VentaDto? {
        return try {
            remoteDataSource.getVenta(id)
        } catch (e: Exception) {
            Log.e("VentasRepository", "getVentas: ${e.message}")
            val venta: VentaDto? = null
            venta
        }
    }

    suspend fun addVentas(venta: VentaDto) = remoteDataSource.addVentas(venta)
    suspend fun deleteVentas(id: Int) = remoteDataSource.delete(id)
    suspend fun updateVentas(ventaDto: VentaDto) = remoteDataSource.update(ventaDto)
    suspend fun findVentas(id: Int) = remoteDataSource.find(id)
}

private fun VentaDto.toVentaEntity() = VentaEntity(
    id = id,
    cliente = cliente,
    total = total,
    totalDescuento = totalDescuento,
    galones = galones,
    precio = precio,
    descuento = descuento

)

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

