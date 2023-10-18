package mx.udg.cucea.conversordedivisas
import retrofit2.http.GET
import retrofit2.http.Query
interface DivisaService {
    @GET("currencies")
    suspend fun getDivisas(
        @Query("apikey") apikey: String
    ): DivisaData
}