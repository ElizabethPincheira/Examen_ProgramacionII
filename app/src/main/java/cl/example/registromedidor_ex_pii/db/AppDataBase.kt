package cl.example.registromedidor_ex_pii.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cl.example.registromedidor_ex_pii.dao.RegistroDao
import cl.example.registromedidor_ex_pii.entities.LocalDateConverter
import cl.example.registromedidor_ex_pii.entities.Registro

@Database(entities = [Registro::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun registroDao(): RegistroDao

}