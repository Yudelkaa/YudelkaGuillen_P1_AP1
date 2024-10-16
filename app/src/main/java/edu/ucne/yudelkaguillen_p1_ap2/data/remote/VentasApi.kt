package edu.ucne.yudelkaguillen_p1_ap2.data.remote

import edu.ucne.yudelkaguillen_p1_ap2.data.remote.dto.VentaDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VentasApi {

    @GET("api/Ventas")
    suspend fun getVentas(): List<VentaDto>

    @PUT("api/Ventas/{id}")
    suspend fun updateVenta(@Body ventaDto: VentaDto)

    @GET("api/Ventas/{id}")
    suspend fun getVenta(@Path("id") id: Int): VentaDto

    @POST("api/Ventas")
    suspend fun addVenta(@Body ventaDto: VentaDto): VentaDto

    @DELETE("api/Ventas/{id}")
    suspend fun deleteVenta(@Path("id") id: Int): Response<Unit>

}