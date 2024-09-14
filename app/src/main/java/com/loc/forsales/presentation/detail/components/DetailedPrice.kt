package com.loc.forsales.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loc.forsales.domain.model.Product
import com.loc.forsales.presentation.Home.components.CompanyLogo
import com.loc.forsales.ui.theme.ForSalesTheme

@Composable
fun DetailedPrice(product: Product, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CompanyLogo(companyName = product.companyName, modifier = Modifier.size(32.dp))

            Text(
                text = "${product.productPrice} ₾",
                textAlign = TextAlign.Right,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Preview
@Composable
fun DetailedPricePreview() {
    ForSalesTheme {
        DetailedPrice(
            product = Product(
                productID = 180,
                companyName = "carrefour",
                productCategory = "ხორცი & ნახევარფაბრიკატები",
                productName = "კუჭი ქათმის ქუალიკო 1 კგ",
                productPrice = "7.95",
                lastUpdated = "60",
                ImgUrl = "https://imageproxy.wolt.com/menu/menu-images/5f6b69ba5d07dca217bd3620/2383c440-f7e4-11ee-8413-cec83d668e87_289623.jpg"
            )
        )
    }
}