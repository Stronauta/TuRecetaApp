package com.example.turecetaapp.presentation.meal_details.favorite_meals

import com.example.turecetaapp.data.local.entities.FavoriteMealEntity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import androidx.navigation.NavController
import com.example.turecetaapp.R
import com.example.turecetaapp.presentation.meal_details.MealDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteMealsScreen(
    viewModel: MealDetailViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getFavoriteMeals()  // Cargar comidas favoritas
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favoritos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.icon_back),
                            contentDescription = "Back Arrow"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(10.dp)
                ) {
                    items(state.favoriteMeals) { meal ->
                        meal?.let {
                            FavoriteMealItem(meal)
                        }
                    }
                }

                if (state.favoriteMeals.isEmpty()) {
                    Text(
                        text = "No tienes comidas favoritas",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize().align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteMealItem(meal: FavoriteMealEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable { /* Navegar a detalles de la comida */ }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = meal.strMeal,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = meal.strCategory,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
