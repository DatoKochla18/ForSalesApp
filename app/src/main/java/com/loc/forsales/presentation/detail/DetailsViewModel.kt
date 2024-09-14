package com.loc.forsales.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.forsales.domain.model.Product
import com.loc.forsales.domain.usecases.GetProduct
import com.loc.forsales.domain.usecases.GetProducts
import com.loc.forsales.presentation.Home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getProductUseCase: GetProduct
) : ViewModel() {

    var state = mutableStateOf(DetailState())
        private set

    fun getProduct(id: Int) {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)

            try {
                getProductUseCase(id = id).collect { products ->
                    state.value = state.value.copy(
                        products = products,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                state.value = state.value.copy(
                    products = emptyList(),
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}
