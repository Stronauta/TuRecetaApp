package com.example.turecetaapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.turecetaapp.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onEnterApp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("RecetaFácil", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(39, 43, 51),
                    titleContentColor = Color.White
                ),/*
                actions = {
                    Image(
                        painter = painterResource(id = R.drawable.chef_hat_clothing_logo_f716ef),
                        contentDescription = "App Icon",
                        modifier = Modifier.size(40.dp)
                    )
                }*/
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            HomeScreenBody(onNavigateToCategories = onEnterApp)
        }
    }
}

@Composable
fun HomeScreenBody(
    onNavigateToCategories: () -> Unit,
) {
    val foodImages = listOf(
        R.drawable.llcbn01574260722,
        R.drawable.hqaejl1695738653,
        R.drawable.n3xxd91598732796,
        R.drawable.oe8rg51699014028,
        R.drawable.rqtxvr1511792990,
        R.drawable.ysxwuq1487323065
    )

    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = currentIndex) {
        delay(6_000)
        val nextIndex = (currentIndex + 1) % foodImages.size
        currentIndex = nextIndex
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = foodImages[currentIndex]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        )

        Button(
            onClick = onNavigateToCategories,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(224,	120,	47),
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = "Iniciar",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreenBody() {
    HomeScreenBody(onNavigateToCategories = {  })
}