package com.lucao.catalogoreceitas.navigation

import kotlinx.serialization.Serializable

sealed interface Rota {

    @Serializable
    data object Splash : Rota

    @Serializable
    data object Login : Rota

    @Serializable
    data object Inicio : Rota

    @Serializable
    data object Receitas : Rota

    @Serializable
    data object Perfil : Rota

    @Serializable
    data class Detalhe(val receitaId: Int) : Rota
}
