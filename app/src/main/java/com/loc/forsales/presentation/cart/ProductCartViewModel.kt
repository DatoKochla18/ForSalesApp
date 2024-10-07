package com.loc.forsales.presentation.cart

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.forsales.domain.model.Product
import com.loc.forsales.domain.usecases.GetProducts
import com.loc.forsales.domain.usecases.SearchForProductCart
import com.loc.forsales.domain.usecases.SearchProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProductCartViewModel @Inject constructor(
    private val searchForProductCart: SearchForProductCart,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(ProductCartState())
    val state: StateFlow<ProductCartState> = _state

    init {
        val cartIds = savedStateHandle.get<String>("cartIds")
        Log.d("ProductCartViewModel", "Received cartIds: $cartIds")
        cartIds?.let { ids ->
            fetchCartProducts(ids.split(",").mapNotNull { it.toIntOrNull() }, mainCategoryID = 2)
        } ?: run {
            Log.e("ProductCartViewModel", "No cartIds received")
            _state.value = _state.value.copy(error = "No cart IDs received")
        }
    }

    private fun fetchCartProducts(productIds: List<Int>,mainCategoryID:Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val result = searchForProductCart(productIds.joinToString(","),mainCategoryId=mainCategoryID)
                result.collect { productCart ->
                    Log.d("ProductCartViewModel", "Fetched products: $productCart")
                    _state.value = _state.value.copy(
                        productsCart = productCart,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                Log.e("ProductCartViewModel", "Error fetching products", e)
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "An unknown error occurred"
                )
            }
        }
    }

    fun updateCartData(newData: List<List<Product>>) {
        _state.value = _state.value.copy(productsCart = newData)
    }

    fun searchProductCart(query: String,mainCategoryID: Int) {
        viewModelScope.launch {
            searchForProductCart(query,mainCategoryID)
                .onStart {
                    _state.value = _state.value.copy(isLoading = true)
                }
                .catch { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "An unknown error occurred"
                    )
                }
                .collect { productCart ->
                    _state.value = _state.value.copy(
                        productsCart = productCart,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }
}