package cl.example.registromedidor_ex_pii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import cl.example.registromedidor_ex_pii.db.AppDatabase
import cl.example.registromedidor_ex_pii.entities.Registro
import cl.example.registromedidor_ex_pii.ui.theme.RegistroMedidor_EX_PIITheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ////cambiar a view model
        lifecycleScope.launch(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,"agendadb"
            ).build()

            val registroDao = db.registroDao()
            val r= Registro(0, 10500,"2024-10-01", "AGUA")
            registroDao.insertAll(r)
        }






        enableEdgeToEdge()
        setContent {
            RegistroMedidor_EX_PIITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RegistroMedidor_EX_PIITheme {
        Greeting("Android")
    }
}