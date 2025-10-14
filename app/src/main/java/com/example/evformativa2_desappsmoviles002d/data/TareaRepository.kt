package com.example.evformativa2_desappsmoviles002d.data

import kotlinx.coroutines.flow.Flow

class TareaRepository (private val dao: TareaDao){
    val tareas: Flow<List<Tarea>> = dao.getAll()

    suspend fun agregar(titulo: String, descripcion: String) {
        require(titulo.isNotBlank()) {"La tarea debe tener un título"}
        require(descripcion.isNotBlank()) {"La tarea debe tener una descripción"}
        dao.insert(Tarea(titulo = titulo.trim(), descripcion = descripcion.trim()))
    }

    suspend fun actualizar(id: Long, titulo: String, descripcion: String) {
        require(id > 0) {"ID Inválido"}
        require(titulo.isNotBlank()) {"La tarea debe tener un título"}
        require(descripcion.isNotBlank()) {"La tarea debe tener una descripción"}
        dao.update(Tarea(id = id, titulo = titulo.trim(), descripcion = descripcion.trim()))
    }

    suspend fun eliminar(tarea: Tarea) = dao.delete(tarea)
    suspend fun obtener(id: Long) = dao.findById(id)
}