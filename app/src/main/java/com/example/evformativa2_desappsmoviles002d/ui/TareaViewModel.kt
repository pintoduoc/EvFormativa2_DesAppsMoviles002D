package com.example.evformativa2_desappsmoviles002d.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evformativa2_desappsmoviles002d.data.Tarea
import com.example.evformativa2_desappsmoviles002d.data.TareaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TareaViewModel (private val repo: TareaRepository): ViewModel(){

    //Lista UI
    val tareas: StateFlow<List<Tarea>> =
        repo.tareas.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _form = MutableStateFlow(TareaFormState())

    val form: StateFlow<TareaFormState> = _form.asStateFlow()

    fun editar(tarea: Tarea?) {
        _form.value = if (tarea == null) {
            TareaFormState()
        } else {
            TareaFormState(
                id = tarea.id,
                titulo = tarea.titulo,
                descripcion = tarea.descripcion
            )
        }
    }

    fun onTituloChange(v: String) { _form.update { it.copy(titulo = v) } }

    fun onDescripcionChange(v: String) {_form.update { it.copy(descripcion = v) } }

    fun limpiarError() { _form.update { it.copy(error = null) } }

    fun guardar(oAlFinal: () -> Unit = {}) = viewModelScope.launch {
        try {
            val f = _form.value

            if (f.id == null) {
                repo.agregar(f.titulo, f.descripcion)
            } else {
                repo.actualizar(f.id, f.titulo, f.descripcion)
            }
            editar(null)
            oAlFinal()
        } catch (e: Exception) {
            _form.update { it.copy(error = e.message ?: "Error desconocido") }
        }
    }

    fun eliminar(tarea: Tarea) = viewModelScope.launch {
        repo.eliminar(tarea)
    }
}