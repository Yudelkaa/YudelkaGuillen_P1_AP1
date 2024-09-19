package edu.ucne.yudelkaguillen_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.yudelkaguillen_p1_ap2.data.local.entity.AlgoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlgoDao {
    @Upsert
    suspend fun save(algo: AlgoEntity)

    @Query("SELECT * FROM Algos")
    suspend fun getAll(): Flow<List<AlgoEntity>>

    @Query("SELECT * FROM Algos WHERE id = :id")
    suspend fun getById(id: Int): AlgoEntity?
    @Query("DELETE FROM Algos WHERE id = :id")
    suspend fun delete(id: Int)

}