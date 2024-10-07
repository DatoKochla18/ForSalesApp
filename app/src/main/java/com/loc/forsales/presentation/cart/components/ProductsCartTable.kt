package com.loc.forsales.presentation.cart.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loc.forsales.domain.model.Product

@Composable
fun ProductsCartTable(productsCart: List<List<Product>>) {
    Log.d("ProductsCartTable", "Displaying products: $productsCart")

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

        // Table Header with "Best Way" and Company Names
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // "Product Name" Header
            Text(
                text = "Product Name",
                modifier = Modifier.weight(2f)
            )

            // "Best Way" Header
            if (productsCart.isNotEmpty()) {
                Text(
                    text = "Best Way",
                    modifier = Modifier.weight(1f)
                )
            }

            // Other company names as headers
            if (productsCart.size > 1) {
                for (i in 1 until productsCart.size) {
                    val companyName = productsCart[i].firstOrNull()?.companyName ?: "Company"
                    Text(
                        text = companyName,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Display products row by row
        val productNames = productsCart.flatten().distinctBy { it.productName }

        productNames.forEach { product ->
            ProductRow(
                productName = product.productName,
                productPrices = productsCart.map { list ->
                    list.find { it.productName == product.productName }?.productPrice ?: "-"
                },
                bestWayCompany = productsCart[0].find { it.productName == product.productName }?.companyImg
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductsCartView() {
    val sampleData = listOf(
        listOf(
            Product(
                productID = 3,
                companyName = "Magniti",
                productCategory = "ხორცი & ნახევარფაბრიკატები",
                productName = "ფილე ქათმის პრემიო 900 გრ",
                ImgUrl = "https://imageproxy.wolt.com/menu/menu-images/640b0b4cc37fe02e9151b630/b1674f42-c0ea-11ee-a2c8-6add7669de06_286245.jpg",
                productPrice = "11.95",
                companyImg = "",
                lastUpdated = "15",
            ),
            Product(
                productID = 5,
                companyName = "Carrefour",
                productCategory = "ხორცი & ნახევარფაბრიკატები",
                productName = "ქათმის ასორტი ბიბილოს ფილე 500 გრ",
                ImgUrl = "https://imageproxy.wolt.com/menu/menu-images/5f6b69ba5d07dca217bd3620/f7b211b8-d705-11ee-9f7a-928a2e3fa858_186288.jpg",
                productPrice = "7.95",
                lastUpdated = "60",
                companyImg = "",
            ),
            Product(
                productID = 5,
                companyName = "Magniti",
                productCategory = "ხორცი & ნახევარფაბრიკატები",
                productName = "ქათმის ასორტი ბიბილოს ფილე 500 გრ",
                ImgUrl = "https://imageproxy.wolt.com/menu/menu-images/5f6b69ba5d07dca217bd3620/f7b211b8-d705-11ee-9f7a-928a2e3fa858_186288.jpg",
                productPrice = "7.95",
                lastUpdated = "15",
                companyImg = "",
            )
        ),
        listOf(
            Product(
                productID = 3,
                companyName = "Carrefour",
                productCategory = "ხორცი & ნახევარფაბრიკატები",
                productName = "ფილე ქათმის პრემიო 900 გრ",
                ImgUrl = "https://imageproxy.wolt.com/menu/menu-images/640b0b4cc37fe02e9151b630/b1674f42-c0ea-11ee-a2c8-6add7669de06_286245.jpg",
                productPrice = "13.95",
                lastUpdated = "60",
                companyImg = "",
            ),
            Product(
                productID = 5,
                companyName = "Carrefour",
                productCategory = "ხორცი & ნახევარფაბრიკატები",
                productName = "ქათმის ასორტი ბიბილოს ფილე 500 გრ",
                ImgUrl = "https://imageproxy.wolt.com/menu/menu-images/5f6b69ba5d07dca217bd3620/f7b211b8-d705-11ee-9f7a-928a2e3fa858_186288.jpg",
                productPrice = "7.95",
                lastUpdated = "60",
                companyImg = "",

            )
        ),
        listOf(
            Product(
                productID = 3,
                companyName = "Magniti",
                productCategory = "ხორცი & ნახევარფაბრიკატები",
                productName = "ფილე ქათმის პრემიო 900 გრ",
                ImgUrl = "https://imageproxy.wolt.com/menu/menu-images/640b0b4cc37fe02e9151b630/b1674f42-c0ea-11ee-a2c8-6add7669de06_286245.jpg",
                productPrice = "11.95",
                lastUpdated = "15",
                companyImg = "",
            ),
            Product(
                productID = 5,
                companyName = "Magniti",
                productCategory = "ხორცი & ნახევარფაბრიკატები",
                productName = "ქათმის ასორტი ბიბილოს ფილე 500 გრ",
                ImgUrl = "https://imageproxy.wolt.com/menu/menu-images/5f6b69ba5d07dca217bd3620/f7b211b8-d705-11ee-9f7a-928a2e3fa858_186288.jpg",
                productPrice = "7.95",
                lastUpdated = "15",
                companyImg = "",
            )
        )
    )

    ProductsCartTable(productsCart = sampleData)
}