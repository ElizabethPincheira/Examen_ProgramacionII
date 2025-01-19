package cl.example.registromedidor_ex_pii.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
data class Registro (
    @PrimaryKey(autoGenerate = true )
    val id:Int,
    val valor_medidor:Int,
    val fecha:String,
    val tipo_medidor:String
)
