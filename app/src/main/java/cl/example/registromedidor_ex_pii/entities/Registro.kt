package cl.example.registromedidor_ex_pii.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.LocalDate

@Entity
data class Registro (
    @PrimaryKey(autoGenerate = true )
    val id:Long?=null,
    val valor_medidor:Int,
    val fecha:LocalDate,
    val tipo_medidor:String
)
