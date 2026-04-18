package com.musaecer.personalfinance.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musaecer.personalfinance.data.remote.dto.AddExpenseRequestDto
import com.musaecer.personalfinance.domain.usecase.expenseusecase.DeleteExpenseUseCase
import com.musaecer.personalfinance.domain.usecase.expenseusecase.GetExpenseByIdUserCase
import com.musaecer.personalfinance.domain.usecase.expenseusecase.GetExpenseUseCase
import com.musaecer.personalfinance.domain.usecase.expenseusecase.InsertExpenseUseCase
import com.musaecer.personalfinance.presentation.state.ExpenseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.musaecer.personalfinance.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val getExpenseUseCase: GetExpenseUseCase,
    private val getExpenseByIdUserCase: GetExpenseByIdUserCase,
    private val addExpenseUseCase: InsertExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
): ViewModel() {
    private val _expenseState = mutableStateOf<ExpenseState>(ExpenseState())
    var state: State<ExpenseState> = _expenseState

    private val _insertStatus = MutableStateFlow<ExpenseState>(ExpenseState())
    val insertStatus = _insertStatus.asStateFlow()

    init {
        getExpenses()
    }

    private var job: Job? = null

    fun getExpenses() {
        job?.cancel()
        job = getExpenseUseCase.invoke()
            .onEach { expenses ->
                when(expenses) {
                    is Resource.Loading -> {
                        _expenseState.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        _expenseState.value = state.value.copy(
                            isLoading = false,
                            error = expenses.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Success -> {
                        _expenseState.value = state.value.copy(
                            isLoading = false,
                            expenses = expenses.data ?: emptyList(),
                            error = ""
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun getExpenseById(id: Int) {
        job?.cancel()

        job = getExpenseByIdUserCase.invoke(id)
            .onEach { expense ->
                when(expense) {
                    is Resource.Loading -> {
                        _expenseState.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        _expenseState.value = state.value.copy(
                            isLoading = false,
                            error = expense.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Success -> {
                        _expenseState.value = state.value.copy(
                            isLoading = false,
                            expense = expense.data,
                            error = ""
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun insertExpense(
        amount: Double,
        description: String,
        date: String,
        categoryId: Int,
        paymentMethodId: Int
    ) {
        viewModelScope.launch {
            // İşlem başladığında Loading durumuna geçiyoruz
            _insertStatus.value = ExpenseState(isLoading = true)

            val expenseRequestDto = AddExpenseRequestDto(
                amount = amount,
                description = description,
                date = date,
                categoryId = categoryId,
                paymentMethodId = paymentMethodId
            )

            when (val result = addExpenseUseCase.invoke(expenseRequestDto)) {
                is Resource.Success -> {
                    // Başarılı olduğunda isInsertSuccess = true yapıyoruz
                    _insertStatus.value = ExpenseState(
                        isLoading = false,
                        isInsertSuccess = true,
                        error = ""
                    )
                    // Kayıt başarılı olduğu için ana listeyi de yeniliyoruz
                    getExpenses()
                }
                is Resource.Error -> {
                    _insertStatus.value = ExpenseState(
                        isLoading = false,
                        isInsertSuccess = false,
                        error = result.message ?: "Beklenmedik bir hata oluştu"
                    )
                }
                is Resource.Loading -> {
                    _insertStatus.value = ExpenseState(isLoading = true)
                }
            }
        }
    }

    // Navigasyon sonrası state'i temizlemek için bu fonksiyonu da eklemelisin
    fun resetInsertStatus() {
        _insertStatus.value = ExpenseState()
    }

    fun deleteExpense(id: Int) {
        viewModelScope.launch {
            val result = deleteExpenseUseCase.invoke(id)
            when(result){
                is Resource.Error -> {
                    _expenseState.value = state.value.copy(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _expenseState.value = state.value.copy(
                        isLoading = true,
                    )
                }
                is Resource.Success -> {
                    _expenseState.value = state.value.copy(
                        isDeleteSuccess = true,
                    )
                    getExpenses()
                }
            }
        }
    }
}