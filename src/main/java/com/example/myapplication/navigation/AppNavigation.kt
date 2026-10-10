package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.screen.RegistroScreen
import com.example.myapplication.ui.screen.ResumenScreen
import com.example.myapplication.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(){
    val navController= rememberNavController()

    // Aquí creamos el viewModel 1 vez
    val usuarioViewModel: UsuarioViewModel= viewModel()

    NavHost(
        navController= navController,
        startDestination = "registro"
    ){
        composable("registro"){
            RegistroScreen(navController, usuarioViewModel)
        }
        composable("resumen"){
            ResumenScreen(usuarioViewModel)
        }
    }
}