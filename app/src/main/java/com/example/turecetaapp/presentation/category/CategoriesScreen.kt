package com.example.turecetaapp.presentation.category

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.example.turecetaapp.data.remote.dto.Category
import com.example.turecetaapp.presentation.authentication.AuthState
import com.example.turecetaapp.presentation.authentication.AuthViewModel
import com.example.turecetaapp.navigation.Screen
import com.example.turecetaapp.ui.theme.TuRecetaAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
    onCategoryItemClick: (String) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(key1 = true) {
        if (uiState.categories.isEmpty()) {
            viewModel.getCategories()
        }
    }

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
                title = { Text("Categorias", color = Color.White) },
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
                    }
                }
            )
        },
    ) { innerPadding ->
        CategoryScreenBody(
            onCategoryItemClick = { category ->
                navController.navigate(Screen.CategoriesMealScreen(category))
            },
            uiState = uiState,
            innerPadding = innerPadding
        )
    }
}


@Composable
fun CategoryScreenBody(
    viewModel: CategoryViewModel = hiltViewModel(),
    onCategoryItemClick: (String) -> Unit,
    uiState: CategoryListState,
    innerPadding: PaddingValues,
) {

    val state by viewModel.state.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = CenterVertically
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(uiState.categories) { category ->
                        CategoryItemRow(
                            categoryItem = category,
                            onCategoryItemClick = onCategoryItemClick
                        )
                    }
                }
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}


@Composable
fun CategoryItemRow(
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
                .padding(PaddingValues(5.dp))
        ) {
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
            HorizontalDivider(
                modifier = Modifier
                    .height(75.dp)
                    .width(1.dp)
                    .align(CenterVertically),
                color = MaterialTheme.colorScheme.primary.copy(0.4f)
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


@Preview(showBackground = true)
@Composable
fun CategoryScreenBodyPreview() {
    // Mock CategoryListState with sample data
    val mockCategoryListState = CategoryListState(
        isLoading = false,
        categories = listOf(
            Category(
                idCategory = "1",
                strCategory = "Italian",
                strCategoryThumb = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUZ2HqUxNOsmIHa2ic1ADGqoGBxjG8UV5cig&s"
            ),
            Category(
                idCategory = "2",
                strCategory = "Mexican",
                strCategoryThumb = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-hDcTP2tH7prQ5YXi7ObvjCH4wKUT4YrkUA&s"
            )
        )
    )

    val onCategoryItemClick: (String) -> Unit = {}

    TuRecetaAppTheme {
        CategoryScreenBody(
            onCategoryItemClick = onCategoryItemClick,
            uiState = mockCategoryListState,
            innerPadding = PaddingValues(0.dp)
        )
    }
}
