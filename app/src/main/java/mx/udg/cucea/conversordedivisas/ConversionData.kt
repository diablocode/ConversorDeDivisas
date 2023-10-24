package mx.udg.cucea.conversordedivisas

import kotlinx.serialization.Serializable
@Serializable
data class ConversionData (
    val data:  Map<String, String>
)
