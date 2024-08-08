package com.example.turecetaapp.presentation.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.turecetaapp.R
import com.example.turecetaapp.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
) {
    val authState by authViewModel.authState.collectAsState()
    val currentUser = authViewModel.currentUser

    val userEmail = when (authState) {
        is AuthState.Authenticated -> currentUser?.email ?: "Unknown"
        else -> "Anonymous"
    }

    val userName = when (authState) {
        is AuthState.Authenticated -> currentUser?.email?.substringBefore('@')?.replaceFirstChar { it.uppercase() } ?: "Unknown"
        else -> "Anonymous"
    }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(authState) {
        if (authState is AuthState.Unauthenticated) {
            navController.navigate(Screen.LoginScreen)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Cerrar Sesión") },
            text = { Text("¿Estás seguro que quieres cerrar sesión?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        authViewModel.signout()
                        showDialog = false
                    }
                ) {
                    /*Text("Sí")*/

                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Confirmar"

                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    /*Text("No")*/

                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "No confirmar"
                    )
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.CategoriesList) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF273133),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icons8_usuario_de_g_nero_neutro_100),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(130.dp)
                    .padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = userName,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = userEmail,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (authState is AuthState.Authenticated || authState is AuthState.Guest) {
                            showDialog = true
                        } else {
                            navController.navigate(Screen.SignupScreen)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Cerrar Sesión"
                    )

                    Text(
                        text = if (authState is AuthState.Authenticated || authState is AuthState.Guest) "  Cerrar Sesión" else "  Registrarse",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                if (authState is AuthState.Authenticated) {
                    Button(
                        onClick = {
                            navController.navigate(Screen.FavoriteScreen)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favoritos"
                        )

                        Text(text = "  Favoritos", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    val authViewModel = AuthViewModel() // Replace with a fake or mock AuthViewModel if necessary
    ProfileScreen(authViewModel, navController)
}
