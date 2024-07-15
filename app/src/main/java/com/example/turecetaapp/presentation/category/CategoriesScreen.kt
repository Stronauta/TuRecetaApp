package com.example.turecetaapp.presentation.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.turecetaapp.data.remote.dto.Category

@Composable
fun CategoryScreen(
   // viewModel: CategoryViewModel,
    uiState: CategoryListState,
//navController: NavController,
    onCategoryItemClick: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
    )  { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Categories", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "See all", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "See all")
            }
            Divider()

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(uiState.categories) { category ->
                    SingleCategoryItem(
                        categoryItem = category,
                        onCategoryItemClick = onCategoryItemClick
                    )
                }
            }
        }
    }
}

@Composable
fun SingleCategoryItem(
    categoryItem: Category,
    onCategoryItemClick: (String) -> Unit
) {

    Card(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onCategoryItemClick(categoryItem.strCategory) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(PaddingValues(5.dp))) {
            AsyncImage(
                model = categoryItem.strCategoryThumb,
                contentDescription = "category-image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .align(CenterVertically)
                    .clip(MaterialTheme.shapes.medium)
            )
            Spacer(
                modifier = Modifier
                    .width(25.dp)
            )
            Divider(
                color = MaterialTheme.colorScheme.primary.copy(0.4f),
                modifier = Modifier
                    .height(75.dp)
                    .width(1.dp)
                    .align(CenterVertically)
            )
            Text(
                text = categoryItem.strCategory,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterVertically)
            )
        }
    }
}

@Preview
@Composable
fun SingleCategoryItemPreview() {
    val sampleCategory = Category(
        idCategory = "1",
        strCategory = "Beef",
        strCategoryThumb = "https://www.themealdb.com/images/category/beef.png"
    )
    SingleCategoryItem(categoryItem = sampleCategory, onCategoryItemClick = {})

}

/*
@Preview
@Composable
fun CategoryScreenPreview() {
    CategoryScreen()
}*/
