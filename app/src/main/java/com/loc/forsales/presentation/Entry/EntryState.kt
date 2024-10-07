package com.loc.forsales.presentation.Entry

import com.loc.forsales.domain.model.MainCategory

data class EntryState (
    val mainCategories:List<MainCategory> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
)
