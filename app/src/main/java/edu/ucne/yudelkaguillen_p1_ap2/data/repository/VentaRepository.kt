package edu.ucne.yudelkaguillen_p1_ap2.data.repository

import edu.ucne.yudelkaguillen_p1_ap2.data.local.dao.VentaDao
import edu.ucne.yudelkaguillen_p1_ap2.data.local.entities.VentaEntity
import javax.inject.Inject

class VentaRepository @Inject constructor(
    private val ventaDao: VentaDao
) {
    suspend fun save(venta: VentaEntity) = ventaDao.save(venta)
    fun getAll() = ventaDao.getAll()
    suspend fun delete(venta: VentaEntity) = ventaDao.delete(venta)
    suspend fun find(id:Int) = ventaDao.find(id)


}
