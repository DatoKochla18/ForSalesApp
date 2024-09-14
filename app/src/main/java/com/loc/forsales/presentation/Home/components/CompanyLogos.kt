package com.loc.forsales.presentation.Home.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.loc.forsales.R

object CompanyLogos {
    val dictionary = mapOf(
        "Carrefour" to R.drawable.carrefour,
        "Magniti" to R.drawable.magniti
    )
}

@Composable
fun CompanyLogo(companyName: String, modifier: Modifier) {
    val logoResId = CompanyLogos.dictionary[companyName] ?: R.drawable.ic_launcher_background
    Image(
        painter = painterResource(id = logoResId),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}