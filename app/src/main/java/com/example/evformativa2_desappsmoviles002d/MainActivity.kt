package com.example.evformativa2_desappsmoviles002d

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.evformativa2_desappsmoviles002d.ui.TareaViewModel
import com.example.evformativa2_desappsmoviles002d.ui.ViewModelFactory
import com.example.evformativa2_desappsmoviles002d.ui.screens.PaginaTareas

class MainActivity : ComponentActivity() {
    private val vm: TareaViewModel by viewModels { ViewModelFactory(application) }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PaginaTareas(vm) }
    }
}