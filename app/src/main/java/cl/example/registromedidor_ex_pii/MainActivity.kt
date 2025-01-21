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
            PantallaFormulario()
        }
    }
}


@Composable
fun OpcionesTiposUi(){
    val tipoMedidores = listOf("Agua", "Luz", "Gas")
    var tipoMedidorSeleccinado by rememberSaveable { mutableStateOf(tipoMedidores[0]) }

    Column(Modifier.selectableGroup()) {
        tipoMedidores.forEach { text ->
            Row(
                Modifier.fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == tipoMedidorSeleccinado),
                        onClick = { tipoMedidorSeleccinado = text },
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
fun PantallaFormulario() {
    var valor_medidor by rememberSaveable { mutableIntStateOf(0) }
    var fecha by rememberSaveable { mutableStateOf("") }

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
        OpcionesTiposUi()

        Button(onClick = {

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
        LazyColumn(
            modifier = Modifier.padding(vertical = it.calculateTopPadding())
        ) {
            items(registros) {
                Text(it.tipo_medidor)
            }
        }
    }

}



