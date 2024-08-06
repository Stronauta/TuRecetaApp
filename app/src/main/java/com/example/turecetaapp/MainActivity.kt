package com.example.turecetaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.turecetaapp.presentation.navigation.TuRecetaNavHost
import com.example.turecetaapp.ui.theme.TuRecetaAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TuRecetaAppTheme {
                Surface {
                    val navController = rememberNavController()
                    TuRecetaNavHost(navController)
                }
            }
        }
    }
}
