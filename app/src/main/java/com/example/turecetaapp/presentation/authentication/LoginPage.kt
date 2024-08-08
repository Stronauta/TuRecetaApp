package com.example.turecetaapp.presentation.authentication

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.turecetaapp.R
import com.example.turecetaapp.navigation.Screen
import isInternetAvailable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chef_hat_clothing_logo_f716ef),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "TuReceta", fontSize = 20.sp)
            }
        }
    )
}

@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        Log.d("LoginPage", "AuthState changed: $authState")
        when (authState) {
            is AuthState.Authenticated -> {
                Log.d("LoginPage", "Authenticated, navigating to HomeScreen")
                navController.navigate(Screen.HomeScreen) {
                    popUpTo(Screen.LoginScreen) { inclusive = true }
                }
            }
            is AuthState.Guest -> {
                Log.d("LoginPage", "Guest, navigating to CategoriesList")
                navController.navigate(Screen.CategoriesList) {
                    popUpTo(Screen.LoginScreen) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                Log.d("LoginPage", "Error: ${(authState as AuthState.Error).message}")
                Toast.makeText(
                    context,
                    (authState as AuthState.Error).message, Toast.LENGTH_SHORT
                ).show()
            }
            else -> Unit
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopBar()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Iniciar Sesión", fontSize = 32.sp)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Contraseña") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image =
                        if (passwordVisible) painterResource(id = R.drawable.icons8_eyes_64) else painterResource(
                            id = R.drawable.icons8_eyes_64__1_
                        )
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = image,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { authViewModel.login(email, password) },
                enabled = authState != AuthState.Loading
            ) {
                Text(text = "Iniciar Sesión")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isInternetAvailable(context)) {
                    TextButton(onClick = {
                        Log.d("LoginPage", "Guest button clicked")
                        authViewModel.enterAsGuest()
                    }) {
                        Text(text = "Entrar como Invitado", fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = " | ", fontSize = 12.sp)

                Spacer(modifier = Modifier.width(8.dp))

                TextButton(onClick = { navController.navigate(Screen.SignupScreen) }) {
                    Text(text = "Registrarse", fontSize = 12.sp)
                }
            }
        }
    }
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
