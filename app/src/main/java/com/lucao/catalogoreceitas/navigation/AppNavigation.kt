package com.lucao.catalogoreceitas.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.lucao.catalogoreceitas.ui.DetalheScreen
import com.lucao.catalogoreceitas.ui.InicioScreen
import com.lucao.catalogoreceitas.ui.LoginScreen
import com.lucao.catalogoreceitas.ui.PerfilScreen
import com.lucao.catalogoreceitas.ui.ReceitasScreen
import com.lucao.catalogoreceitas.ui.SplashScreen
import com.lucao.catalogoreceitas.viewmodel.ReceitasViewModel

private class ItemMenu(
    val titulo: String,
    val rota: Rota,
    val icone: ImageVector
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: ReceitasViewModel = viewModel()

    val entradaAtual by navController.currentBackStackEntryAsState()
    val destinoAtual = entradaAtual?.destination

    val itensMenu = listOf(
        ItemMenu("Início", Rota.Inicio, Icons.Filled.Home),
        ItemMenu("Receitas", Rota.Receitas, Icons.AutoMirrored.Filled.List),
        ItemMenu("Perfil", Rota.Perfil, Icons.Filled.Person)
    )

    val mostrarBarra = itensMenu.any { item ->
        destinoAtual?.hierarchy?.any { it.hasRoute(item.rota::class) } == true
    }

    Scaffold(
        bottomBar = {
            if (mostrarBarra) {
                NavigationBar {
                    itensMenu.forEach { item ->
                        val selecionado = destinoAtual?.hierarchy?.any {
                            it.hasRoute(item.rota::class)
                        } == true

                        NavigationBarItem(
                            selected = selecionado,
                            onClick = {
                                navController.navigate(item.rota) {
                                    popUpTo<Rota.Inicio> {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icone,
                                    contentDescription = null
                                )
                            },
                            label = { Text(item.titulo) }
                        )
                    }
                }
            }
        }
    ) { paddingInterno ->
        NavHost(
            navController = navController,
            startDestination = Rota.Splash,
            modifier = Modifier.padding(paddingInterno)
        ) {
            composable<Rota.Splash> {
                SplashScreen(
                    onConcluido = {
                        if (viewModel.logado) {
                            navController.navigate(Rota.Inicio) {
                                popUpTo<Rota.Splash> { inclusive = true }
                            }
                        } else {
                            navController.navigate(Rota.Login) {
                                popUpTo<Rota.Splash> { inclusive = true }
                            }
                        }
                    }
                )
            }

            composable<Rota.Login> {
                LoginScreen(
                    viewModel = viewModel,
                    onEntrou = {
                        navController.navigate(Rota.Inicio) {
                            popUpTo<Rota.Login> { inclusive = true }
                        }
                    }
                )
            }

            composable<Rota.Inicio> {
                InicioScreen(viewModel = viewModel)
            }

            composable<Rota.Receitas> {
                ReceitasScreen(
                    viewModel = viewModel,
                    onAbrirDetalhe = { receitaId ->
                        navController.navigate(Rota.Detalhe(receitaId))
                    }
                )
            }

            composable<Rota.Perfil> {
                PerfilScreen(
                    viewModel = viewModel,
                    onSair = {
                        navController.navigate(Rota.Login) {
                            popUpTo<Rota.Inicio> { inclusive = true }
                        }
                    }
                )
            }

            composable<Rota.Detalhe> { entrada ->
                val rota = entrada.toRoute<Rota.Detalhe>()
                DetalheScreen(
                    viewModel = viewModel,
                    receitaId = rota.receitaId,
                    onVoltar = { navController.popBackStack() }
                )
            }
        }
    }
}
