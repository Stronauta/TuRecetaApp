package com.example.turecetaapp.presentation.meal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.turecetaapp.data.remote.dto.Meal
import com.example.turecetaapp.data.repository.MealRepository
import com.example.turecetaapp.util.Constants.PARAM_STR_AREA
import com.example.turecetaapp.util.Constants.PARAM_STR_CATEGORY
import com.example.turecetaapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val repository: MealRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(MealUiState())
    open val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {

            refreshData()


            savedStateHandle.get<String>(PARAM_STR_CATEGORY)?.let { strCategory ->
                getMeals(strCategory)
            }
            savedStateHandle.get<String>(PARAM_STR_AREA)?.let { strArea ->
                getMealsByArea(strArea)
            }
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            try {
                // Fetch and save all categories
                repository.fetchAndSaveCategories()

                // Optionally fetch and save meals for all categories
                _uiState.value.meals.forEach { meal ->
                    meal.strMeal.let { category ->
                        repository.fetchAndSaveMealsByCategory(category)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = MealUiState(error = e.message)
            }
        }
    }

    fun getMealsByArea(strArea: String) {
        try {
            viewModelScope.launch {
                repository.getMealsByArea(strArea).onEach { result ->
                    when (result) {
                        is Resource.Loading -> _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }

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
                }.launchIn(viewModelScope)
            }
        } catch (e: Exception) {

        }
    }


    fun getMeals(strCategory: String) {
        try {
            viewModelScope.launch {
                repository.getMealsByCategory(strCategory).onEach { result ->
                    when (result) {
                        is Resource.Loading -> _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }

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
                }.launchIn(viewModelScope)
            }
        } catch (e: Exception) {
            _uiState.value = MealUiState(error = e.message)
        } finally {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}


data class MealUiState(
    val meals: List<Meal> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null

)
