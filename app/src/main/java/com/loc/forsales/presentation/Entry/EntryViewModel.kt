package com.loc.forsales.presentation.Entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.forsales.domain.usecases.GetMainCategories
import com.loc.forsales.presentation.Home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class EntryViewModel @Inject constructor(
    private val GetMainCategoriesUseCase:GetMainCategories
) :ViewModel() {
    private val _state = MutableStateFlow(EntryState())
    val state: StateFlow<EntryState> = _state

    init {
        getMainCategories()
    }
    private fun getMainCategories() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            GetMainCategoriesUseCase()
                .catch { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred"
                    )
                }
                .collect { categories ->
                    _state.value = _state.value.copy(
                        mainCategories = categories,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }

}