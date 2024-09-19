package edu.ucne.yudelkaguillen_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.yudelkaguillen_p1_ap2.data.local.dao.AlgoDao

@Database(
    entities = [
    ],


    version = 1,
    exportSchema = false
)
abstract class ParcialDatabase : RoomDatabase() {
    abstract fun algoDao(): AlgoDao

}