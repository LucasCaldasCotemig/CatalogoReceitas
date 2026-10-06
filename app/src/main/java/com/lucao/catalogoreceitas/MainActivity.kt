package com.lucao.catalogoreceitas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.lucao.catalogoreceitas.navigation.AppNavigation
import com.lucao.catalogoreceitas.ui.theme.CatalogoReceitasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatalogoReceitasTheme {
                AppNavigation()
            }
        }
    }
}
