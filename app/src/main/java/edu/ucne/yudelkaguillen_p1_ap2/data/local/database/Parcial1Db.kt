package edu.ucne.yudelkaguillen_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.yudelkaguillen_p1_ap2.data.local.dao.VentaDao
import edu.ucne.yudelkaguillen_p1_ap2.data.local.entities.VentaEntity

@Database(
    entities = [VentaEntity::class],
    version = 2,
    exportSchema = false
)
abstract class ParcialDatabase : RoomDatabase() {
    abstract fun ventaDao(): VentaDao

}