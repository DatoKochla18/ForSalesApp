package com.loc.forsales.presentation.navgraph

import androidx.navigation.NamedNavArgument

sealed class Route(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object EntryScreen : Route(route = "entryScreen")
    object HomeScreen : Route(route = "homeScreen")
    object DetailsScreen : Route(route = "detailsScreen")
    object CartScreen : Route(route = "cartScreen")
}
