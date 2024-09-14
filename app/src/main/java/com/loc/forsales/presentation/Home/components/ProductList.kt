package com.loc.forsales.presentation.Home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loc.forsales.domain.model.Product
import com.loc.forsales.presentation.Dimens.ProductCardSize
import com.loc.forsales.ui.theme.ForSalesTheme

@Composable
fun ProductList(
    products: List<Product>,
    modifier: Modifier = Modifier,
    onClick: (Product) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductCard(product = product, onClick = { onClick(product) })
        }
    }
}

@Preview
@Composable
fun ProductListPreview() {
    ForSalesTheme {
        ProductList(
            products = listOf(
                Product(
                    productID = 180,
                    companyName = "carrefour",
                    productCategory = "ხორცი & ნახევარფაბრიკატები",
                    productName = "კუჭი ქათმის ქუალიკო 1 კგ",
                    productPrice = "7.95",
                    lastUpdated = "60",
                    ImgUrl = "https://imageproxy.wolt.com/menu/menu-images/5f6b69ba5d07dca217bd3620/2383c440-f7e4-11ee-8413-cec83d668e87_289623.jpg"
                ),
                Product(
                    productID = 181,
                    companyName = "Magniti",
                    productCategory = "ხორცი & ნახევარფაბრიკატები",
                    productName = "კუჭი ქათმის ქუალიკო 1 კგ",
                    productPrice = "8.45",
                    lastUpdated = "15",
                    ImgUrl = "https://imageproxy.wolt.com/menu/menu-images/5f6b69ba5d07dca217bd3620/2383c440-f7e4-11ee-8413-cec83d668e87_289623.jpg"
                )
            ), onClick = {  }
        )
    }
}