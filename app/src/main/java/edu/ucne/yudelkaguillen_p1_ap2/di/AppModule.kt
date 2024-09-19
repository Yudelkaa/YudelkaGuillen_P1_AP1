package edu.ucne.yudelkaguillen_p1_ap2.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.ucne.yudelkaguillen_p1_ap2.data.local.database.ParcialDatabase
import javax.inject.Singleton

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
    fun provideAlgoDao(parcialDatabase: ParcialDatabase) =
        parcialDatabase.algoDao()

}