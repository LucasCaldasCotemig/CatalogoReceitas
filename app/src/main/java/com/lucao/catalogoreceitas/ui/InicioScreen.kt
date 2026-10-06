package com.lucao.catalogoreceitas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lucao.catalogoreceitas.viewmodel.ReceitasViewModel

@Composable
fun InicioScreen(viewModel: ReceitasViewModel, modifier: Modifier = Modifier) {
    var nome by rememberSaveable { mutableStateOf("") }
    var tempo by rememberSaveable { mutableStateOf("") }
    var ingredientes by rememberSaveable { mutableStateOf("") }
    var modoPreparo by rememberSaveable { mutableStateOf("") }
    var mensagem by rememberSaveable { mutableStateOf("") }

    val tempoNumero = tempo.toIntOrNull()
    val formularioValido = nome.isNotBlank() &&
        tempoNumero != null &&
        ingredientes.isNotBlank() &&
        modoPreparo.isNotBlank()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Olá, ${viewModel.usuario}!",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Seu catálogo",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${viewModel.receitas.size} receitas cadastradas",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Text(
            text = "Nova receita",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome da receita") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = tempo,
            onValueChange = { tempo = it },
            label = { Text("Tempo de preparo (minutos)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = ingredientes,
            onValueChange = { ingredientes = it },
            label = { Text("Ingredientes (separados por vírgula)") },
            minLines = 2,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = modoPreparo,
            onValueChange = { modoPreparo = it },
            label = { Text("Modo de preparo") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.adicionarReceita(
                    nome = nome,
                    tempoMinutos = tempoNumero ?: 0,
                    ingredientes = ingredientes,
                    modoPreparo = modoPreparo
                )
                nome = ""
                tempo = ""
                ingredientes = ""
                modoPreparo = ""
                mensagem = "Receita adicionada! Veja na aba Receitas."
            },
            enabled = formularioValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar receita")
        }

        if (mensagem.isNotEmpty()) {
            Text(
                text = mensagem,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
