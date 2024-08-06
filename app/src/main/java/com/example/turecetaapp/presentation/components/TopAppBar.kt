package com.example.turecetaapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.turecetaapp.presentation.authentication.AuthState
import com.example.turecetaapp.presentation.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    navController: NavController,
    title: String,
    authState: AuthState,
    onProfileClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Unauthenticated -> navController.navigate(Screen.LoginScreen)
            else -> Unit
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        topBar = {
            TopAppBar(
                title = { Text(title, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(39, 43, 51),
                    titleContentColor = Color.White
                ),
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clickable { onProfileClick() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile Icon",
                                tint = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            )
        },
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    val navController = rememberNavController()
    val authState = AuthState.Authenticated // Mock authenticated state
    val onProfileClick = {}

    TopAppBar(
        navController = navController,
        title = "Preview Title",
        authState = authState,
        onProfileClick = onProfileClick
    ) { innerPadding ->
        // Mock content
        Box(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Content goes here", color = Color.White)
        }
    }
}
