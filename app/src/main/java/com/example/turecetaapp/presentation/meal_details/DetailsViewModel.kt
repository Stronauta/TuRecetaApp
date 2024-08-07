package com.example.turecetaapp.presentation.meal_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.turecetaapp.data.remote.dto.MealDetails
import com.example.turecetaapp.data.repository.MealRepository
import com.example.turecetaapp.util.Constants.PARAM_ID_MEAL
import com.example.turecetaapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val repository: MealRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(MealDetailState())
    val state: StateFlow<MealDetailState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(PARAM_ID_MEAL)?.let { mealId ->
                getMealDetails(mealId)
            }
        }
    }

    fun getMealDetails(id: String) {
        viewModelScope.launch {
            repository.getMealById(id).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = MealDetailState(
                            meals = listOf(result.data)
                        )
                    }
                    is Resource.Error -> {
                        _state.value = MealDetailState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = MealDetailState(isLoading = true)
                    }
                }
            }.launchIn(this)
        }
    }
}

data class MealDetailState(
    val isLoading: Boolean = false,
    val meals: List<MealDetails?> = emptyList(),
    val error: String = ""
)
