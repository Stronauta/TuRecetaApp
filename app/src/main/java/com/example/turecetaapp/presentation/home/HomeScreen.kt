package com.example.turecetaapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import com.example.turecetaapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCategories: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("RecetaFácil", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF3B5059),
                    titleContentColor = Color.White
                ),

                actions = {

                    Image(
                        painter = painterResource(id = R.drawable._439221),
                        contentDescription = "App Icon",
                        modifier = Modifier.size(40.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            HomeScreenBody(onNavigateToCategories = onNavigateToCategories)
        }
    }
}


@Composable
fun HomeScreenBody(
    onNavigateToCategories: () -> Unit,
) {
    val foodImages = listOf(
        "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg",
        "https://www.themealdb.com/images/media/meals/n3xxd91598732796.jpg",
        "https://www.themealdb.com/images/media/meals/ysxwuq1487323065.jpg",
        "https://www.themealdb.com/images/media/meals/rqtxvr1511792990.jpg",
        "https://www.themealdb.com/images/media/meals/xr0n4r1576788363.jpg"
    )

    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = currentIndex) {
        delay(6_000)
        val nextIndex = (currentIndex + 1) % foodImages.size
        currentIndex = nextIndex
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(16.dp))
                .shadow(10.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = foodImages[currentIndex]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Button(
            onClick = onNavigateToCategories,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = "Entrar",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreenBody() {
    HomeScreenBody(onNavigateToCategories = { /* Define what happens here, e.g., navigation */ })
}