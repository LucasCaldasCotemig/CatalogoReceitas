package com.lucao.catalogoreceitas.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.lucao.catalogoreceitas.model.Receita

class ReceitasViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencias =
        application.getSharedPreferences("catalogo_receitas", Context.MODE_PRIVATE)

    private val listaReceitas = mutableStateListOf(
        Receita(
            id = 1,
            nome = "Macarrão ao Alho e Óleo",
            tempoMinutos = 20,
            ingredientes = listOf(
                "200 g de espaguete",
                "4 dentes de alho",
                "4 colheres de azeite",
                "Sal e pimenta-do-reino",
                "Salsinha picada"
            ),
            modoPreparo = "Cozinhe o macarrão em água fervente com sal até ficar al dente. Doure o alho fatiado no azeite sem deixar queimar. Misture o macarrão escorrido ao alho, tempere com pimenta e finalize com a salsinha."
        ),
        Receita(
            id = 2,
            nome = "Bolo de Cenoura",
            tempoMinutos = 55,
            ingredientes = listOf(
                "3 cenouras médias",
                "4 ovos",
                "1 xícara de óleo",
                "2 xícaras de açúcar",
                "2 xícaras de farinha de trigo",
                "1 colher de fermento"
            ),
            modoPreparo = "Bata no liquidificador as cenouras, os ovos, o óleo e o açúcar. Misture com a farinha e o fermento. Asse em forma untada por cerca de 40 minutos a 180 graus."
        ),
        Receita(
            id = 3,
            nome = "Omelete de Queijo",
            tempoMinutos = 10,
            ingredientes = listOf(
                "3 ovos",
                "50 g de queijo muçarela",
                "1 colher de manteiga",
                "Sal a gosto"
            ),
            modoPreparo = "Bata os ovos com sal. Derreta a manteiga na frigideira, despeje os ovos e adicione o queijo. Dobre o omelete ao meio quando firmar."
        ),
        Receita(
            id = 4,
            nome = "Arroz de Forno",
            tempoMinutos = 40,
            ingredientes = listOf(
                "3 xícaras de arroz cozido",
                "200 g de presunto picado",
                "200 g de queijo ralado",
                "1 lata de milho verde",
                "1 caixa de creme de leite"
            ),
            modoPreparo = "Misture o arroz, o presunto, o milho e o creme de leite. Coloque em um refratário, cubra com o queijo e leve ao forno até gratinar."
        ),
        Receita(
            id = 5,
            nome = "Vitamina de Banana",
            tempoMinutos = 5,
            ingredientes = listOf(
                "2 bananas maduras",
                "1 copo de leite gelado",
                "1 colher de mel",
                "Gelo a gosto"
            ),
            modoPreparo = "Bata todos os ingredientes no liquidificador até ficar cremoso e sirva em seguida."
        )
    )

    private var proximoId = 6

    var usuario by mutableStateOf(preferencias.getString("usuario", "") ?: "")
        private set

    val logado: Boolean
        get() = usuario.isNotBlank()

    val receitas: List<Receita>
        get() = listaReceitas

    fun entrar(nome: String) {
        usuario = nome.trim()
        preferencias.edit().putString("usuario", usuario).apply()
    }

    fun sair() {
        usuario = ""
        preferencias.edit().remove("usuario").apply()
    }

    fun adicionarReceita(
        nome: String,
        tempoMinutos: Int,
        ingredientes: String,
        modoPreparo: String
    ) {
        val listaIngredientes = ingredientes
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
        listaReceitas.add(
            Receita(
                id = proximoId,
                nome = nome.trim(),
                tempoMinutos = tempoMinutos,
                ingredientes = listaIngredientes,
                modoPreparo = modoPreparo.trim()
            )
        )
        proximoId++
    }

    fun excluirReceita(id: Int) {
        listaReceitas.removeAll { it.id == id }
    }

    fun buscarReceita(id: Int): Receita? {
        return listaReceitas.firstOrNull { it.id == id }
    }
}
