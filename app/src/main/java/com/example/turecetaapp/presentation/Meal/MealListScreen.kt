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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.unit.dp
import com.example.turecetaapp.data.remote.dto.Meal
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.turecetaapp.R
import com.example.turecetaapp.presentation.components.HeadingTextComponent

/*@Composable
fun MealScreen(
    viewModel: MealViewModel = hiltViewModel(),
    goBack: NavController,
    onMealItemClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        if(uiState.meals.isEmpty()) {
            viewModel.getMeals()
        }
    }

    MealBody(
        uiState = uiState,
        onMealItemClick = onMealItemClick,
        goBack = goBack
    )
}*/

@Composable
fun MealBody(
    onMealItemClick: (String) -> Unit,
    goBack: NavController,
    viewModel: MealViewModel = hiltViewModel()
){
    val state by viewModel.uiState.collectAsState()


    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back_icon",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(start = 5.dp, end = 10.dp, top = 10.dp)
                        .clip(CircleShape)
                        .size(30.dp)
                        .clickable(
                            onClick = {
                                goBack.popBackStack()
                            }
                        )
                        .alignByBaseline()
                )
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
                        onMealItemClick = onMealItemClick
                    )
                }
            }
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
