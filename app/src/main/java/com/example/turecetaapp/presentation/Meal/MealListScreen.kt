package com.example.turecetaapp.presentation.Meal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.turecetaapp.data.local.entities.IngredientEntity
import com.example.turecetaapp.data.local.entities.MealEntity
import com.example.turecetaapp.data.remote.dto.Meal
import com.example.turecetaapp.presentation.components.HeadingTextComponent
import com.example.turecetaapp.util.Constants.PARAM_STR_CATEGORY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealListScreen(
    viewModel: MealViewModel = hiltViewModel(),
    navController: NavController,
    onMealItemClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        if(uiState.meals.isEmpty()) {
            viewModel.getMeals(PARAM_STR_CATEGORY)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        topBar = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back_icon",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(start = 5.dp, end = 10.dp, top = 10.dp)
                    .clip(CircleShape)
                    .size(30.dp)
                    .clickable(
                        onClick = {
                            navController.popBackStack()
                        }
                    )

            )
            TopAppBar(
                title = { Text("RecetaFácil", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(39, 43, 51),
                    titleContentColor = Color.White
                ),
                actions = {
                    Row {
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = "Navigation Menu",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        },
    ) {  innerPadding ->
        MealScreenBody(
            onMealItemClick = onMealItemClick,
            viewModel = viewModel,
            innerPadding = innerPadding,
            navController = navController
        )

    }
}

@Composable
fun MealScreenBody(
    onMealItemClick: () -> Unit,
    navController: NavController,
    viewModel: MealViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
){
    val state by viewModel.uiState.collectAsState()


    Box(
        Modifier.fillMaxSize()
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
                            navController.navigate("meal_detail_screen/$mealId")
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
){

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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(140.dp)
            ){
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


@Preview(showBackground = true,)
@Composable
fun MealPreview() {

    val sampleMeal = Meal(
        idMeal = "53082",
        strMeal = "Strawberries Romanoff",
        strMealThumb = "https://www.themealdb.com/images/media/meals/oe8rg51699014028.jpg"
    )

    
    MealItemRow(mealsItem = sampleMeal, onMealItemClick = {})
}


@Composable
fun RecipeListItem(meal: MealEntity) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(
            Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            if (!meal.strMealThumb.isNullOrEmpty()) {
                AsyncImage(
                    model = meal.strMealThumb,
                    contentDescription = "thumbnail",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = meal.strMeal ?: "",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Ingredients",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = getIngredients(meal)
            )
            Spacer(modifier = Modifier.padding(8.dp))

            AnimatedVisibility(visible = expanded) {
                Column {
                    Text(
                        text = "Instructions",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = meal.strInstructions ?: ""
                    )
                }
            }
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded = !expanded
                    }) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Clear",
                    tint = Color.Black,
                    modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )

                )
            }

        }
    }
}

fun getIngredients(meal: IngredientEntity): String {
    var ingredients = ""

    with(meal) {
        if(!ingredient.isNullOrEmpty()) ingredients += "$ingredient - $measure\n"
    }
    return ingredients.trimEnd('\n')
}
