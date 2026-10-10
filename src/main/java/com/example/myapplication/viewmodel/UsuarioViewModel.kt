package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.UsuarioErrores
import com.example.myapplication.model.UsuarioUiState
import com.example.myapplication.model.data.local.UsuarioDatabase
import com.example.myapplication.model.data.local.UsuarioEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class UsuarioViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = UsuarioDatabase.getDatabase(app).usuarioDao()

    //Estado interno mutable
    private val _estado = MutableStateFlow(UsuarioUiState())

    val estado: StateFlow<UsuarioUiState> = _estado

    fun onNombreChange(valor: String){
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor: String){
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor: String){
        _estado.update { it.copy(clave= valor, errores= it.errores.copy(clave= null)) }
    }

    fun onDireccionChange(valor: String){
        _estado.update { it.copy(direccion= valor, errores= it.errores.copy(direccion = null)) }
    }

    fun onAceptarTerminosChange(valor: Boolean){
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    fun validarFormulario(): Boolean{
        val estadoActual= _estado.value
        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if (!estadoActual.correo.contains("@")) "Correo inválido" else null,
            clave = if (estadoActual.clave.length<6) "Debe tener al menos 6 dígitos" else null,
            direccion = if (estadoActual.direccion.isBlank()) "Campo obligatorio" else null
        )

        val hayErrores= listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }

    // Agregar
    // Guarda en Room
    fun guardar(onListo: () -> Unit) {
        val a = _estado.value
        viewModelScope.launch {
            dao.insertar(
                UsuarioEntity(
                    nombre = a.nombre,
                    correo = a.correo,
                    clave = a.clave,
                    direccion = a.direccion
                )
            )
            onListo()
        }
    }
//Hasta aqui
}