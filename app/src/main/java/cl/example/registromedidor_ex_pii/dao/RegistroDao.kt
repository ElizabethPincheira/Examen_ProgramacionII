package cl.example.registromedidor_ex_pii.dao

import androidx.room.*
import cl.example.registromedidor_ex_pii.entities.Registro


@Dao
interface RegistroDao {

    @Query("SELECT*FROM registro ORDER BY fecha DESC")
    suspend fun getAll(): List<Registro>

   // @Query("SELECT*FROM registro WHERE id =:id")
    //suspend fun obtenerPorId(id:Int): Registro

    @Insert
    suspend fun insertAll(registro: Registro)

    @Update
    suspend fun modificar(registro: Registro)

    //@Delete
    //fun delete(registro: Registro)
}