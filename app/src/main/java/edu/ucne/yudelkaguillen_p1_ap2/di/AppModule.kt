package edu.ucne.yudelkaguillen_p1_ap2.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.yudelkaguillen_p1_ap2.data.local.database.ParcialDatabase
import edu.ucne.yudelkaguillen_p1_ap2.data.remote.VentasApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
            "Parcial.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideVentaDao(parcialDatabase: ParcialDatabase) =
        parcialDatabase.ventaDao()

    const val BASE_URL = "https://ventas-api-egd4dkh2g7brh7b8.canadacentral-01.azurewebsites.net"

    @Singleton
    @Provides
    fun providesMoshi(): Moshi =
        Moshi.Builder().add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesVentasApi(moshi: Moshi): VentasApi {
       return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(VentasApi::class.java)
    }
}
