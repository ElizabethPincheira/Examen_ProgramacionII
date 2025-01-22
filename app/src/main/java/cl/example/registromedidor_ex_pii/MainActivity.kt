package cl.example.registromedidor_ex_pii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.example.registromedidor_ex_pii.entities.Registro
import cl.example.registromedidor_ex_pii.ui.ListaRegistrosViewModel
import org.w3c.dom.Text
import java.time.LocalDate
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

//import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.filled.Lightbulb
//import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Info




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            appRegistrosUI()
        }
    }
}

@Composable
fun appRegistrosUI(
    navController: NavHostController = rememberNavController(),
    vmListaRegistro: ListaRegistrosViewModel =  viewModel(factory = ListaRegistrosViewModel.Factory)
) {
    NavHost(
        navController = navController,
        startDestination = "inicio"
    )
    {
        composable("inicio") {
            PantallaListaRegistros(
                registros = vmListaRegistro.registros,
                onAdd = { navController.navigate("form")}
                )
        }
        composable("form") {
            PantallaFormulario(
                vmListaRegistro = vmListaRegistro,
                onRegistroExitoso = {navController.popBackStack()}
            )
        }
    }
}


@Composable
fun OpcionesTiposUi(
    onTipoMedidorSeleccionado:(String) -> Unit
){
    val tipoMedidores = listOf("Agua", "Luz", "Gas")
    var tipoMedidorSeleccinado by rememberSaveable { mutableStateOf(tipoMedidores[0]) }

    Column(Modifier.selectableGroup()) {
        tipoMedidores.forEach { text ->
            Row(
                Modifier.fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == tipoMedidorSeleccinado),
                        onClick = { tipoMedidorSeleccinado = text
                            onTipoMedidorSeleccionado(text)},
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == tipoMedidorSeleccinado),
                    onClick = null // null recommended for accessibility with screenreaders
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}



//@Preview(showSystemUi = true)

@Composable
fun PantallaFormulario(
    vmListaRegistro: ListaRegistrosViewModel,
    onRegistroExitoso:()->Unit
) {
    var valor_medidor by rememberSaveable { mutableIntStateOf(0) }
    var fecha by rememberSaveable { mutableStateOf("")}
    var tipo_medidor by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 20.dp )
    ) {
        //valor_medidor, fecha, tipo_medidor
        TextField(
            value = valor_medidor.toString(),
            onValueChange = { valor_medidor = it.toIntOrNull() ?: 0 },
            label = {Text("Medidor")}
        )

        TextField(
            value = fecha,
            onValueChange = { fecha = it},
            placeholder = {Text("2024-12-25")},
            label = {Text("Fecha")}
        )
        Text("Medidor de:")
        OpcionesTiposUi(
            onTipoMedidorSeleccionado = { tipoMedidorSeleccinado ->
                tipo_medidor = tipoMedidorSeleccinado
            }
        )

        Button(onClick = {
            val nuevoRegistro = Registro(null,valor_medidor, LocalDate.now(), tipo_medidor)
            vmListaRegistro.insertarRegistro(nuevoRegistro)
            onRegistroExitoso()
        }){
            Text("Registrar medicion")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PantallaListaRegistros(
    registros: List<Registro> = listOf(),
    onAdd:() ->Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
            onAdd()
            }){
                Icon(imageVector = Icons.Filled.Add, contentDescription = "+")
            }
        }
    ){
            paddingValues ->
        LazyColumn (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(registros) { registro ->
                RegistroItem(registro = registro)
                Divider()   // Línea divisoria entre elementos

            }
//        }
//        LazyColumn(
//            modifier = Modifier.padding(vertical = it.calculateTopPadding())
//        ) {
//            items(registros) {
//                Text(it.tipo_medidor)
//            }
        }
    }
}


@Composable
fun RegistroItem(registro: Registro) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícono representativo del tipo de medición
        val icon = when (registro.tipo_medidor) {
            "Agua" -> Icons.Filled.Menu
            //"Luz" -> Icons.Filled.Lightbulb
            //"Gas" -> Icons.Filled.LocalGasStation
            else -> Icons.Filled.Info
        }

        Icon(
            imageVector = icon,
            contentDescription = registro.tipo_medidor,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 16.dp)
        )

        // Datos del registro
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = registro.tipo_medidor,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Medición: ${registro.valor_medidor}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Fecha: ${registro.fecha}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}



