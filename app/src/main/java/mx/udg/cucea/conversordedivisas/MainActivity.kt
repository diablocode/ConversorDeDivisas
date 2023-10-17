package mx.udg.cucea.conversordedivisas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
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
                    Currencies("Android")
                }
            }
        }
    }
}

@Composable
fun Currencies(name: String, modifier: Modifier = Modifier) {
    var items by remember {
        mutableStateOf(emptyList<String>())
    }
    // state of the menu
    var expanded by remember {
        mutableStateOf(true)
    }
    var selectedItem by remember { mutableStateOf("") }
    items = arrayOf("Favorites", "Options", "Settings", "Share").toList()
    Column {
        Button(onClick = { expanded = !expanded }) {
            Text(text = "mostrar lista")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEachIndexed { itemIndex, itemValue ->
                DropdownMenuItem(
                    onClick = {
                        selectedItem = itemValue
                        expanded = false
                    },
                    text = {Text(itemValue)}
                )
            }

        }
        Text(
            text = selectedItem,
            modifier = modifier
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConversorDeDivisasTheme {
        Currencies("Android")
    }
}