package com.example.evformativa2_desappsmoviles002d.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.evformativa2_desappsmoviles002d.data.AppDatabase
import com.example.evformativa2_desappsmoviles002d.data.TareaRepository

class ViewModelFactory(app: Application): ViewModelProvider.Factory {
    private val repo by lazy {
        val dao = AppDatabase.Companion.get(app).tareaDao()
        TareaRepository(dao)
    }
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create (modelClass: Class<T>) =
        TareaViewModel(repo) as T
}