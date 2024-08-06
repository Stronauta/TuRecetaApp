import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.turecetaapp.data.remote.dto.MealDetails

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
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 10.dp)
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
        ingredients.forEach { (ingredient, measure) ->
            Row(
                verticalAlignment = Alignment.CenterVertically
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
