package edu.ucne.yudelkaguillen_p1_ap2.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.yudelkaguillen_p1_ap2.data.local.database.ParcialDatabase
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideParcialDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            ParcialDatabase::class.java,
            "Parcial.db").fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideVentaDao(parcialDatabase: ParcialDatabase) =
        parcialDatabase.ventaDao()

}