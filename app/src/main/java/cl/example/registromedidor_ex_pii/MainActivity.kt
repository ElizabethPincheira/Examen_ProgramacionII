package cl.example.registromedidor_ex_pii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.example.registromedidor_ex_pii.entities.Registro
import cl.example.registromedidor_ex_pii.ui.ListaRegistrosViewModel
import org.w3c.dom.Text
import java.time.LocalDate
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.items



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
    vmListaRegistro: ListaRegistrosViewModel =  viewModel(factory = ListaRegistrosViewModel.Factory)
){
    //se ejecuta una ves al iniciar composable

    LaunchedEffect(Unit) {
        vmListaRegistro.obtenerRegistros()
    }
    LazyColumn {
        items(vmListaRegistro.registros){
            Text(it.tipo_medidor)
        }
        item{
            Button(onClick={
                vmListaRegistro.insertarRegistro(Registro(null,10400, LocalDate.now(),"agua"))
            }){
                Text("Agregar jejejej")
            }
        }
    }
}


