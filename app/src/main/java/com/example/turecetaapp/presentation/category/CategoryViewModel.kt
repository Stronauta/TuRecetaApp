package com.example.turecetaapp.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.turecetaapp.data.remote.dto.Category
import com.example.turecetaapp.data.repository.MealRepository
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
class CategoryViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryListState())
    val state: StateFlow<CategoryListState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getCategories()
        }
    }

     fun getCategories() {
        viewModelScope.launch {
            repository.getCategories().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = CategoryListState(
                            categories = result.data ?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        _state.value = CategoryListState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = CategoryListState(isLoading = true)
                    }
                }
            }.launchIn(this)
        }
    }
}

data class CategoryListState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val error: String = ""
)
