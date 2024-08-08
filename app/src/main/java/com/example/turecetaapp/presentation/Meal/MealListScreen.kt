package com.example.turecetaapp.presentation.Meal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.turecetaapp.data.remote.dto.Meal
import com.example.turecetaapp.presentation.authentication.AuthState
import com.example.turecetaapp.presentation.authentication.AuthViewModel
import com.example.turecetaapp.presentation.components.HeadingTextComponent
import com.example.turecetaapp.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealListScreen(
    category: String,
    onMealItemClick: (String) -> Unit,
    viewModel: MealViewModel = hiltViewModel(),
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(key1 = category) {
        viewModel.getMeals(category)
    }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Unauthenticated -> navController.navigate(Screen.LoginScreen)
            else -> Unit
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(category, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Arrow",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(39, 43, 51),
                    titleContentColor = Color.White
                ),
                actions = {
                    Row(
                        verticalAlignment = CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clickable {
                                    navController.navigate(Screen.ProfileScreen)
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile Icon",
                                tint = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
/*                        IconButton(onClick = { *//* Handle search button click *//* }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = Color.White
                            )
                        }*/
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(Modifier.fillMaxSize()) {
                LazyColumn(
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(uiState.meals) { meal ->
                        MealItemRow(
                            mealsItem = meal,
                            onMealItemClick = { mealId ->
                                navController.navigate(Screen.MealDetailScreen(mealId))
                            })
                    }
                }
            }

            uiState.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .align(Alignment.Center)
                )
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
    }
}


@Composable
fun MealScreenBody(
    onMealsItemClick: () -> Unit,
    navController: NavController,
    viewModel: MealViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
) {
    val state by viewModel.uiState.collectAsState()


    Box(
        Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = CenterVertically
            ) {

                HeadingTextComponent(
                    text = "Meals",
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(state.meals) { platos ->
                    MealItemRow(
                        mealsItem = platos,
                        onMealItemClick = { mealId ->
                            // navController.navigate("meal_detail_screen/$mealId")
                        }
                    )
                }
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}


@Composable
fun MealItemRow(
    mealsItem: Meal,
    onMealItemClick: (String) -> Unit
) {

    Card(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .heightIn(max = 140.dp)
            .clickable { onMealItemClick(mealsItem.idMeal) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = CenterVertically
        ) {
            Box(
                modifier = Modifier.size(140.dp)
            ) {
                AsyncImage(
                    model = mealsItem.strMealThumb,
                    contentDescription = "dish-image",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                )
            }
            Text(
                text = mealsItem.strMeal,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MealPreview() {

    val sampleMeal = Meal(
        idMeal = "53082",
        strMeal = "Strawberries Romanoff",
        strMealThumb = "https://www.themealdb.com/images/media/meals/oe8rg51699014028.jpg"
    )


    MealItemRow(mealsItem = sampleMeal, onMealItemClick = {})
}
