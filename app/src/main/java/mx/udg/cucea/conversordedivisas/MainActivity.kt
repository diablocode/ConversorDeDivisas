package mx.udg.cucea.conversordedivisas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.udg.cucea.conversordedivisas.ui.theme.ConversorDeDivisasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConversorDeDivisasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val divisaModel = DivisaViewModel()
                    Currencies(divisaModel)
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Currencies(divisasModel: DivisaViewModel, modifier: Modifier = Modifier) {
    var divisasData by remember { mutableStateOf<DivisaData?>(null) }

    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }
    var seleccion by remember { mutableStateOf("") }
    var selectedOrigen by remember { mutableStateOf("") }
    var selectedDestino by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }

    val apiKey = "XXX" // modificar por tu API key
    divisasModel.viewModelScope.launch(Dispatchers.IO) {
        try {
            divisasData = divisasModel.getCurrency(apiKey)
            Log.i("divisas", divisasData.toString())
        } catch (e: Exception) {
            divisasData = null
            Log.i("divisasError", e.message!!)
        }
    }
    Column {
        Row{
            Button(onClick = { expanded = !expanded; seleccion = "origen" }
                ) {
                Text(text = "seleccionar origen")
            }
            Text(
                text = selectedOrigen,
                modifier = modifier
            )
        }
        Row {
            Button(onClick = { expanded = !expanded; seleccion = "destino" }) {
                Text(text = "seleccionar destino")
            }

            Text(
                text = selectedDestino,
                modifier = modifier
            )
        }
        if (selectedOrigen != "" && selectedDestino != ""){
            divisasModel.viewModelScope.launch(Dispatchers.IO) {
                try {
                    valor = divisasModel.getConversion(
                        apiKey,
                        selectedOrigen,
                        selectedDestino
                    ).data[selectedDestino]
                        .toString()
                    Log.i("divisas", valor)

                } catch (e: Exception) {
                    divisasData = null
                    Log.i("divisasError", e.message!!)
                }
            }
            Text(text = "El tipo de cambio es: $valor")
        }
    }


    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        divisasData?.data?.forEach {  itemValue ->
            DropdownMenuItem(
                onClick = {
                    if (seleccion == "origen"){
                        selectedOrigen = itemValue.value.code
                    }
                    else {
                        selectedDestino = itemValue.value.code
                    }

                    expanded = false
                },
                text = {Text(itemValue.value.name)}
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConversorDeDivisasTheme {
        Currencies(DivisaViewModel())
    }
}