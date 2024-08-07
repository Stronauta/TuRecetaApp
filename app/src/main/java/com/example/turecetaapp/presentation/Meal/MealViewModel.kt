package com.example.turecetaapp.presentation.Meal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.turecetaapp.data.local.entities.FavoriteMealEntity
import com.example.turecetaapp.data.remote.dto.Meal
import com.example.turecetaapp.data.remote.dto.MealDetails
import com.example.turecetaapp.data.repository.MealRepository
import com.example.turecetaapp.presentation.meal_details.toFavoriteMealEntity
import com.example.turecetaapp.util.Constants.PARAM_STR_CATEGORY
import com.example.turecetaapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val repository: MealRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(MealUiState())
    val uiState: StateFlow<MealUiState> = _uiState.asStateFlow()

    init {
        savedStateHandle.get<String>(PARAM_STR_CATEGORY)?.let { strCategory ->
            getMeals(strCategory)
        }
    }

    fun getMeals(strCategory: String) {
        viewModelScope.launch {
            repository.getMealsByCategory(strCategory).collect { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resource.Success -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            meals = result.data ?: emptyList()
                        )
                    }

                    is Resource.Error -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    private fun getFavoriteMeals() {
        viewModelScope.launch {
            repository.getFavoriteMeals().collect { favoriteMeals ->
                _uiState.update { it.copy(favoriteMeals = favoriteMeals) }
            }
        }
    }

    fun toggleFavoriteMeal(mealDetails: MealDetails) {
        viewModelScope.launch {
            val favoriteMeal = mealDetails.toFavoriteMealEntity()
            if (_uiState.value.favoriteMeals.any { it.idMeal == mealDetails.idMeal }) {
                repository.removeFavoriteMeal(favoriteMeal)
            } else {
                repository.addFavoriteMeal(favoriteMeal)
            }
            getFavoriteMeals()  // Refresh the favorite meals list
        }
    }

}


data class MealUiState(
    val meals: List<Meal> = emptyList(),
    val favoriteMeals: List<FavoriteMealEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null

)
