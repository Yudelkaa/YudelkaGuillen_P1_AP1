package edu.ucne.yudelkaguillen_p1_ap2.data.repository

import edu.ucne.yudelkaguillen_p1_ap2.data.local.dao.AlgoDao
import edu.ucne.yudelkaguillen_p1_ap2.data.local.entity.AlgoEntity
import javax.inject.Inject

class AlgoRepository @Inject constructor(
    private val algoDao: AlgoDao
) {
    suspend fun save(algo: AlgoEntity) = algoDao.save(algo)
    suspend fun getAll() = algoDao.getAll()
    suspend fun getById(id: Int) = algoDao.getById(id)
    suspend fun delete(id: Int) = algoDao.delete(id)


}