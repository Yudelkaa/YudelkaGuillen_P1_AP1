package edu.ucne.yudelkaguillen_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.yudelkaguillen_p1_ap2.data.local.entities.VentaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VentaDao {
    @Upsert
    suspend fun save(venta: VentaEntity)

    @Query(
        """
        SELECT * 
        FROM Algos 
        WHERE id=:id  
        LIMIT 1
        """
    )
    suspend fun find (id: Int): VentaEntity?

    @Delete
    suspend fun delete(id: VentaEntity)

    @Query("SELECT * FROM Algos")
    fun getAll(): Flow<List<VentaEntity>>


}