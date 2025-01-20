package cl.example.registromedidor_ex_pii

import android.app.Application
import androidx.room.Room
import cl.example.registromedidor_ex_pii.db.AppDatabase

class Aplicacion : Application() {
    val db by lazy { Room.databaseBuilder(this, AppDatabase::class.java, "registro.db").build()}
    val registroDao by lazy { db.registroDao() }
}