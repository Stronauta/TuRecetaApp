import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.turecetaapp.R
import com.example.turecetaapp.data.remote.dto.MealDetails
import com.example.turecetaapp.navigation.Screen
import com.example.turecetaapp.presentation.authentication.AuthState
import com.example.turecetaapp.presentation.authentication.AuthViewModel
import com.example.turecetaapp.presentation.components.TextTitleMealInfo
import com.example.turecetaapp.presentation.meal_details.MealDetailViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailScreen(
    mealId: String,
    viewModel: MealDetailViewModel = hiltViewModel(),
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(mealId) {
        viewModel.getMealDetails(mealId)
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
                title = { Text("Meal Info", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.icon_back),
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
            Column(
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
            ) {
                state.meals.firstOrNull()?.let { meal ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        )
                    ) {
                        MealDetailItem(mealInfo = meal)
                        Spacer(modifier = Modifier.height(10.dp))

                        TextTitleMealInfo("Ingredients")
                        MealIngredients(mealInfo = meal)
                        Spacer(modifier = Modifier.height(10.dp))

                        TextTitleMealInfo("Instructions")
                        MealInstructions(mealInfo = meal)

                        Spacer(modifier = Modifier.height(10.dp))

                        TextTitleMealInfo("Video en YouTube")

                        if (isInternetAvailable(context)) {
                            Text(
                                text = "Más información sobre la receta",
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        val intent =
                                            Intent(Intent.ACTION_VIEW, Uri.parse(meal.strYoutube))
                                        context.startActivity(intent)
                                    }
                            )
                        } else {
                            Text(
                                text = "No hay conexión a internet",
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                            )
                        }
                    }
                } ?: run {
                    if (state.error.isNotBlank()) {
                        Text(
                            text = state.error,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        )
                    }
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun MealDetailItem(
    mealInfo: MealDetails
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(MaterialTheme.shapes.medium)
        ) {
            AsyncImage(
                model = mealInfo.strMealThumb,
                contentDescription = "dish-image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
            )
        }
        Text(
            text = mealInfo.strMeal,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        )
        Text(
            text = mealInfo.strCategory,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )
        Text(
            text = mealInfo.strArea,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 10.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun MealIngredients(
    mealInfo: MealDetails
) {
    val ingredients = listOf(
        mealInfo.strIngredient1 to mealInfo.strMeasure1,
        mealInfo.strIngredient2 to mealInfo.strMeasure2,
        mealInfo.strIngredient3 to mealInfo.strMeasure3,
        mealInfo.strIngredient4 to mealInfo.strMeasure4,
        mealInfo.strIngredient5 to mealInfo.strMeasure5,
        mealInfo.strIngredient6 to mealInfo.strMeasure6,
        mealInfo.strIngredient7 to mealInfo.strMeasure7,
        mealInfo.strIngredient8 to mealInfo.strMeasure8,
        mealInfo.strIngredient9 to mealInfo.strMeasure9,
        mealInfo.strIngredient10 to mealInfo.strMeasure10
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        ingredients.filter { it.first.isNotEmpty() }.forEach { (ingredient, measure) ->
            Row(
                verticalAlignment = CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 10.dp)
                        .size(8.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                )
                Text(
                    text = "$ingredient - $measure",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun MealInstructions(
    mealInfo: MealDetails
) {
    val instructions = mealInfo.strInstructions
        .replace("\\r\\n", "\n")
        .replace("\n", "\n\n")
        .trim()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = instructions,
            textAlign = TextAlign.Justify,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
    }
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
