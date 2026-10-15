package com.example.projeto_desenvolvimento_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.projeto_desenvolvimento_mobile.ui.theme.Projeto_desenvolvimento_mobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // tema claro fixo: as telas usam cores fixas do design
            Projeto_desenvolvimento_mobileTheme(darkTheme = false, dynamicColor = false) {
                TelaPrincipal()
            }
        }
    }
}
