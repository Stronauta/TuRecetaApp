package com.example.turecetaapp.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.turecetaapp.data.remote.dto.Meal
import com.example.turecetaapp.data.repository.MealRepository
import com.example.turecetaapp.util.Constants.PARAM_STR_CATEGORY
import com.example.turecetaapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MealUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            SavedStateHandle().get<String>(PARAM_STR_CATEGORY)?.let { strCategory ->
                getMeals(strCategory)
            }
        }
    }

private suspend fun getMeals(strCategory: String) {
    _uiState.value = MealUiState(isLoading = true)
    try {
        viewModelScope.launch {
            repository.getMealsByCategory(strCategory).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.value = MealUiState(meals = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _uiState.value = MealUiState(error = result.message)
                    }
                    is Resource.Loading -> {
                        _uiState.value = MealUiState(isLoading = true)
                    }
                }
            }
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