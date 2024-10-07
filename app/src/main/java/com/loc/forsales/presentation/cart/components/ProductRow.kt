package com.loc.forsales.presentation.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.loc.forsales.presentation.Home.components.CompanyLogo


@Composable
fun ProductRow(productName: String, productPrices: List<String>, bestWayCompany: String?) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Product Name Column
        Text(
            text = productName,
            modifier = Modifier.weight(2f),
        )

        // Price Columns (for Best Way and other companies)
        productPrices.forEachIndexed { index, price ->
            if (index == 0) { // Best Way column
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Display the price
                    Text(
                        text = price,
                        modifier = Modifier.weight(1f)
                    )

                    // Spacer between price and logo
                    Spacer(modifier = Modifier.width(8.dp))

                    // Display the company logo at the end
                    if (bestWayCompany != null) {
                        CompanyLogo(companyImgUrl = bestWayCompany, modifier = Modifier.size(24.dp))
                    }
                }
            } else {
                // For other columns, just display the price
                Text(
                    text = price,
                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                )
            }
        }
    }
}