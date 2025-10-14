package com.example.evformativa2_desappsmoviles002d.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evformativa2_desappsmoviles002d.ui.TareaViewModel

@Composable
fun TareaDialog(
    vm: TareaViewModel,
    onDismiss: () -> Unit,
    onSaved:() -> Unit
) {
    val form by vm.form.collectAsState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (form.id == null) "Nueva tarea" else "Editar tarea") },
        text = {
            Column (Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = form.titulo,
                    onValueChange = vm::onTituloChange,
                    label = { Text("Titulo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = form.descripcion,
                    onValueChange = vm::onDescripcionChange,
                    label = { Text("Descripción") },
                    singleLine = false,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { vm.guardar(onSaved)}) {
                Text(if (form.id == null) "Guardar" else "Actualizar")
            }
        },
        dismissButton = {TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}