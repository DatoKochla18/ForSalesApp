package com.loc.forsales.presentation.Home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.forsales.domain.model.Product
import com.loc.forsales.domain.usecases.GetProducts
import com.loc.forsales.domain.usecases.SearchProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProducts,
    private val searchProductsUseCase: SearchProducts
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    private var searchJob: Job? = null

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getProductsUseCase()
                .catch { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred"
                    )
                }
                .collect { products ->
                    val categories = products.map { it.productCategory }.distinct()
                    _state.value = _state.value.copy(
                        products = products,
                        filteredProducts = filterProducts(products, _state.value.selectedCategory, _state.value.query),
                        categories = listOf("All") + categories,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }

    fun updateQuery(query: String) {
        _state.value = _state.value.copy(query = query)
        searchProducts(query)
    }

    fun searchProducts(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            _state.value = _state.value.copy(isLoading = true)

            searchProductsUseCase(query)
                .onEach { searchResults ->
                    val filteredProducts = filterProducts(searchResults, _state.value.selectedCategory, query)
                    _state.value = _state.value.copy(
                        filteredProducts = filteredProducts,
                        isLoading = false,
                        error = null
                    )
                }
                .catch { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "Error during search"
                    )
                }
                .launchIn(this)
        }
    }


    fun filterByCategory(category: String) {
        viewModelScope.launch {
            _state.value = _state.value.let { currentState ->
                val newFilteredProducts = filterProducts(currentState.products, category, currentState.query)
                currentState.copy(
                    selectedCategory = category,
                    filteredProducts = newFilteredProducts
                )
            }
        }
    }

    private fun filterProducts(products: List<Product>, category: String, query: String): List<Product> {
        return products.filter { product ->
            (category == "All" || product.productCategory == category) &&
                    (query.isEmpty() || product.productName?.contains(query, ignoreCase = true) == true)
        }
    }
}

