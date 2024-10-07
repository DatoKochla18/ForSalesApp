package com.loc.forsales

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.loc.forsales.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.compose.runtime.State
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    // Using MutableState to track the start destination
    private val _startDestination = mutableStateOf(Route.EntryScreen.route)
    val startDestination: State<String> = _startDestination

    init {
        // No need to set _startDestination.value again since it's already initialized
    }
}
