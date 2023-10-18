package mx.udg.cucea.conversordedivisas

import kotlinx.serialization.Serializable

@Serializable
data class DivisaData (
    val data:  Map<String, CurrencyData>
)
@Serializable
data class CurrencyData (
    val name: String,
    val code: String
)
