package com.musaecer.personalfinance.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musaecer.personalfinance.data.remote.dto.AddCategoryRequestDto
import com.musaecer.personalfinance.domain.model.Category
import com.musaecer.personalfinance.domain.usecase.categoryusecase.GetCategoryUseCase
import com.musaecer.personalfinance.domain.usecase.categoryusecase.InsertCategoryUseCase
import com.musaecer.personalfinance.presentation.state.CategoryState
import com.musaecer.personalfinance.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
): ViewModel() {
    private val _state = mutableStateOf<CategoryState>(CategoryState())
    val state: State<CategoryState> = _state

    private var job: Job? = null

    init {
        getCategories()
    }

    fun getCategories() {
        job?.cancel()
        // UseCase'den gelen Flow'u dinliyoruz
        job = getCategoryUseCase.invoke()
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            categories = result.data ?: emptyList(),
                            error = ""
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message ?: "Beklenmedik bir hata oluştu"
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun insertCategory(category: String) {
        viewModelScope.launch {
            val category = AddCategoryRequestDto(
                category = category
            )
            val result = insertCategoryUseCase(category)
            when(result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(isInsertSuccess = true)
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isInsertSuccess = false,
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> TODO()
            }
        }
    }
}