package com.loc.forsales.presentation.Home

import android.util.Log
import androidx.compose.runtime.State
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
    private val _selectedProductIds = mutableStateOf<List<Int>>(emptyList())
    val selectedProductIds: State<List<Int>> = _selectedProductIds

    private var currentPage = 1
    private var currentPageForSearch = 1
    private var isLastPage = false
    private var isLoadingMore = false
    private var currentCategoryId: Int = -1

    fun loadProductsByCategory(categoryId: Int) {
        if (categoryId != currentCategoryId) {
            currentCategoryId = categoryId
            currentPage = 1
            isLastPage = false
            isLoadingMore = false
            getProducts(categoryId)
        }
    }

    private fun getProducts(categoryId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getProductsUseCase(categoryId, currentPage, pageSize = 15)
                .catch { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred"
                    )
                }
                .collect { products ->
                    if (currentPage == 1) {
                        _state.value = _state.value.copy(
                            products = products,
                            filteredProducts = products,
                            isLoading = false,
                            error = null
                        )
                    } else {
                        _state.value = _state.value.copy(
                            products = _state.value.products + products,
                            filteredProducts = _state.value.filteredProducts + products,
                            isLoading = false,
                            error = null
                        )
                    }
                    isLoadingMore = false
                }
        }
    }

    fun loadMoreProducts() {
        if (!isLoadingMore && !isLastPage) {
            isLoadingMore = true
            if (state.value.query.isEmpty()) {
                currentPage += 1
                getProducts(currentCategoryId)
            } else {
                currentPageForSearch += 1
                searchProducts(state.value.query, currentCategoryId)
            }
        }
    }



    fun updateQuery(query: String,categoryId: Int) {
        _state.value = _state.value.copy(query = query)
        searchProducts(query=query,categoryId=categoryId)
    }

    fun searchProducts(query: String,categoryId:Int) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            _state.value = _state.value.copy(isLoading = true)

            searchProductsUseCase(query=query, mainCategoryId = categoryId, pageSize = 15, page = currentPageForSearch)
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

    fun toggleProductSelection(productId: Int) {
        _selectedProductIds.value = if (_selectedProductIds.value.contains(productId)) {
            _selectedProductIds.value - productId
        } else {
            _selectedProductIds.value + productId
        }
    }

    fun fetchCartIds(): String {
        return selectedProductIds.value.joinToString(",")
    }

}


