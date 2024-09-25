package edu.ucne.yudelkaguillen_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.yudelkaguillen_p1_ap2.data.local.entities.AlgoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlgoDao {
    @Upsert
    suspend fun save(algo: AlgoEntity)

    @Query(
        """
        SELECT * 
        FROM Algos 
        WHERE id=:id  
        LIMIT 1
        """
    )
    suspend fun find (id: Int): AlgoEntity?

    @Delete
    suspend fun delete(id: AlgoEntity)

    @Query("SELECT * FROM Algos")
    fun getAll(): Flow<List<AlgoEntity>>


}