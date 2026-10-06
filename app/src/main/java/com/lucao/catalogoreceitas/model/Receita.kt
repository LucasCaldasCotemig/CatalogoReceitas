package com.lucao.catalogoreceitas.model

data class Receita(
    val id: Int,
    val nome: String,
    val tempoMinutos: Int,
    val ingredientes: List<String>,
    val modoPreparo: String
)
