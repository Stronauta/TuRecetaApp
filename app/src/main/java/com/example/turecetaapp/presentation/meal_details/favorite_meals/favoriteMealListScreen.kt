package com.example.turecetaapp.presentation.meal_details.favorite_meals

import com.example.turecetaapp.data.local.entities.FavoriteMealEntity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import androidx.navigation.NavController
import com.example.turecetaapp.R
import com.example.turecetaapp.navigation.Screen
import com.example.turecetaapp.presentation.meal_details.MealDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    navController: NavController
) {
    TopAppBar(
        title = { Text(title, color = Color.White) },
        navigationIcon = {
            IconButton(onClick = { navController.navigate(Screen.ProfileScreen) }) {
                Icon(
                    painter = painterResource(R.drawable.icon_back),
                    contentDescription = "Back Arrow"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF273133),
            titleContentColor = Color.White
        )
    )
}


@Composable
fun FavoriteMealsScreen(
    viewModel: MealDetailViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var selectedMeal by remember { mutableStateOf<FavoriteMealEntity?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getFavoriteMeals()
    }

    Scaffold(
        topBar = {
            TopBar(title = "Favoritos", navController = navController)
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
                            FavoriteMealItem(meal, onMealClick = { selectedMeal = it })
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

            selectedMeal?.let {
                RecipeDetailDialog(
                    meal = it,
                    onDismiss = { selectedMeal = null },
                    onDelete = { mealToDelete ->
                        viewModel.deleteFavoriteMeal(mealToDelete)
                        selectedMeal = null
                    }
                )
            }
        }
    }
}

@Composable
fun FavoriteMealItem(
    meal: FavoriteMealEntity,
    onMealClick: (FavoriteMealEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onMealClick(meal) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
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

@Composable
fun RecipeDetailDialog(
    meal: FavoriteMealEntity,
    onDismiss: () -> Unit,
    onDelete: (FavoriteMealEntity) -> Unit
) {
    var showConfirmationDialog by remember { mutableStateOf(false) }

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text("Confirmar Eliminación") },
            text = { Text("¿Estás seguro de que quieres eliminar esta comida favorita?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(meal)
                        showConfirmationDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Red // Color del texto del botón
                    )
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showConfirmationDialog = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Gray // Color del texto del botón
                    )
                ) {
                    Text("Cancelar")
                }
            }
        )
    } else {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {

                    Text(
                        text = "${meal.strMeal}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    AsyncImage(
                        model = meal.strMealThumb,
                        contentDescription = meal.strMeal,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )


                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Ingredientes:",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Column {
                        val ingredients = listOf(
                            meal.strIngredient1, meal.strIngredient2, meal.strIngredient3,
                            meal.strIngredient4, meal.strIngredient5, meal.strIngredient6,
                            meal.strIngredient7, meal.strIngredient8, meal.strIngredient9,
                            meal.strIngredient10,
                        )

                        ingredients.filter { it.isNotBlank() }.forEach { ingredient ->
                            Text(
                                text = "- $ingredient",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "instrucciones:",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Recipe: ${meal.strInstructions}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = { showConfirmationDialog = true },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color.Red // Color del texto del botón
                            )
                        ) {
                            Text("Eliminar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(onClick = onDismiss) {
                            Text("Cerrar")
                        }
                    }
                }
            }
        }
    }
}
