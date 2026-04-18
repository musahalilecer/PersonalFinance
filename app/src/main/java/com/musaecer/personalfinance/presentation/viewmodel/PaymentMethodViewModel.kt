package com.musaecer.personalfinance.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musaecer.personalfinance.data.remote.dto.AddPaymentMethodRequestDto
import com.musaecer.personalfinance.domain.usecase.paymentmethodusecase.GetPaymentMethodUseCase
import com.musaecer.personalfinance.domain.usecase.paymentmethodusecase.InsertPaymentMethodUseCase
import com.musaecer.personalfinance.presentation.state.ExpenseState
import com.musaecer.personalfinance.presentation.state.PaymentMethodState
import com.musaecer.personalfinance.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentMethodViewModel @Inject constructor(
    private val paymentMethodUseCase: GetPaymentMethodUseCase,
    private val insertPaymentMethodUseCase: InsertPaymentMethodUseCase
): ViewModel() {
    private val _paymentMethodsState = mutableStateOf<PaymentMethodState>(PaymentMethodState())
    val paymentMethodsState: State<PaymentMethodState> = _paymentMethodsState

    private val _paymentInsertStatus = MutableStateFlow<ExpenseState>(ExpenseState())
    val paymentInsertStatus = _paymentInsertStatus.asStateFlow()


        init {
            getPaymentMethods()
        }

    fun getPaymentMethods() {
        paymentMethodUseCase.invoke().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _paymentMethodsState.value = paymentMethodsState.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _paymentMethodsState.value = paymentMethodsState.value.copy(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Success -> {
                    _paymentMethodsState.value = paymentMethodsState.value.copy(
                        isLoading = false,
                        paymentMethods = result.data ?: emptyList(),
                        error = ""
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun insertPaymentMethod(
        name: String,
        description: String,
        type: String,
    ) {
        viewModelScope.launch {
            try {
                _paymentMethodsState.value = paymentMethodsState.value
                val paymentMethodDto = AddPaymentMethodRequestDto(
                    name = name,
                    description = description,
                    type = type
                )
                val result = insertPaymentMethodUseCase.invoke(paymentMethodDto)
                when(result){
                    is Resource.Error -> {
                        _paymentMethodsState.value = paymentMethodsState.value.copy(
                            isLoading = false,
                            error = result.message ?: "Failed to insert payment method"
                        )
                    }
                    is Resource.Loading -> {
                        _paymentMethodsState.value = paymentMethodsState.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _paymentMethodsState.value = paymentMethodsState.value.copy(
                            isLoading = false,
                            error = "",
                        )
                        getPaymentMethods() // Refresh the list after successful insertion
                    }
                }
            }
            catch (e: Exception) {
                _paymentMethodsState.value = paymentMethodsState.value.copy(
                    isLoading = false,
                    error = "Failed to insert payment method: ${e.localizedMessage}"
                )
            }
        }
    }
}