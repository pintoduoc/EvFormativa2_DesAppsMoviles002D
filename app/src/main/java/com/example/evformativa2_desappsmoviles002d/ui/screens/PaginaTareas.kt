package com.example.evformativa2_desappsmoviles002d.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.evformativa2_desappsmoviles002d.data.Tarea
import com.example.evformativa2_desappsmoviles002d.ui.TareaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaginaTareas(vm: TareaViewModel) {
    val tareas by vm.tareas.collectAsStateWithLifecycle()
    val formState by vm.form.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    var toDelete by remember { mutableStateOf<Tarea?>(null) }

    if (formState.error != null) {
        AlertDialog(
            onDismissRequest = {vm.limpiarError()},
            confirmButton = { TextButton(onClick = {vm.limpiarError()})  { Text("OK") } },
            title = { Text("Error") },
            text = { Text(formState.error ?: "") }
        )
    }

    if (showDialog) {
        TareaDialog(
            vm = vm,
            onDismiss = {showDialog = false},
            onSaved = {showDialog = false}
        )
    }

    if(toDelete != null) {
        AlertDialog(
            onDismissRequest = { toDelete = null},
            confirmButton = {
                TextButton(onClick = {
                    toDelete?.let(vm::eliminar)
                    toDelete = null
                }) { Text("Eliminar") }
            },
            dismissButton = { TextButton(onClick = { toDelete = null }) { Text("Cancelar") }},
            title = {Text("Confirmar eliminación")},
            text = {Text("¿Seguro que deseas elliminar '${toDelete?.titulo}'?")}
        )
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Tus Tareas") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                vm.editar(null)
                showDialog = true
            }) { Text("+") }
        }
    ) {padding ->
        if (tareas.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
Text("No Hay tareas. Presiona + para agregar.")
            }
        } else {
            LazyColumn(Modifier.fillMaxSize().padding(padding)) {
                items(tareas, key = {it.id}) {p ->
                    TareaItem(
                        tarea = p,
                        onEdit = {
                            vm.editar(p)
                            showDialog = true
                        },
                        onDelete = {toDelete = p}
                    )
                }
            }
        }
    }
}

@Composable
private fun TareaItem(
    tarea: Tarea,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    Card(Modifier.fillMaxWidth().padding(8.dp)) {
        Row(
            Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
        }
        Column(Modifier.clickable { onEdit() }) {
            Text(tarea.titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(tarea.descripcion, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            Text("ID: ${tarea.id}", style = MaterialTheme.typography.bodySmall)
        }
        Row {
            TextButton(onClick = onEdit) { Text("Editar") }
            TextButton(onClick = onDelete) { Text("Eliminar") }
        }
    }
}