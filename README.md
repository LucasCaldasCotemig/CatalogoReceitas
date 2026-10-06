# Catálogo de Receitas

App Android feito com Jetpack Compose para a atividade de Navegação Android (DEVAND).

## Tema escolhido

Catálogo de Receitas: o usuário entra com o nome, vê as receitas cadastradas, abre os detalhes de cada uma, cadastra novas e exclui as que não quer mais.

## Telas

- Splash: tela de abertura de 1,5 segundo. Depois decide para onde ir, Login ou Início, conforme existe usuário salvo.
- Login: campo de nome e botão Entrar. O nome fica salvo em SharedPreferences.
- Início: saudação, quantidade de receitas e formulário para cadastrar uma receita.
- Receitas: lista com todas as receitas. Tocar em uma abre os detalhes, o ícone da lixeira exclui.
- Detalhe: tempo de preparo, ingredientes e modo de preparo. Recebe o id da receita pela rota.
- Perfil: dados do usuário e botão Sair, que volta para o Login.

## Navegação

- Rotas tipadas com `sealed interface Rota` e `@Serializable`, usando `composable<T>` e `toRoute()` no Detalhe.
- Um único `NavHost` em `navigation/AppNavigation.kt`.
- Barra inferior com Início, Receitas e Perfil, exibida só nas telas principais.
- Troca de aba com `popUpTo`, `saveState`, `restoreState` e `launchSingleTop`.
- Splash e Login são removidos da pilha com `popUpTo(... inclusive = true)`, então o botão voltar não retorna a eles.
- Um único `ReceitasViewModel` é compartilhado entre todas as telas.

## Como rodar no Android Studio

1. Abra o Android Studio e escolha File > Open, selecionando a pasta `CatalogoReceitas`.
2. Aguarde o Gradle Sync terminar. Se o Android Studio sugerir atualizar o Gradle ou o plugin do Android, aceite.
3. Crie ou escolha um emulador (API 24 ou superior) ou conecte um celular.
4. Clique em Run (Shift + F10).

Versões usadas: Android Gradle Plugin 8.5.2, Kotlin 2.0.21, Gradle 8.9, Compose BOM 2024.09.03, Navigation Compose 2.8.2.
