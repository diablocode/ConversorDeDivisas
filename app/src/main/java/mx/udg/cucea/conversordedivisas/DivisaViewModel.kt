package mx.udg.cucea.conversordedivisas

import androidx.lifecycle.ViewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class DivisaViewModel: ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.freecurrencyapi.com/v1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val service = retrofit.create(DivisaService::class.java)

    suspend fun getCurrency( apiKey: String): DivisaData {
        val response =  service.getDivisas(apiKey)
        return response
    }
}