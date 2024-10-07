package com.loc.forsales.presentation.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.loc.forsales.domain.model.Product
import com.loc.forsales.presentation.Dimens
import com.loc.forsales.presentation.detail.components.DetailedPrice
import com.loc.forsales.ui.theme.ForSalesTheme

@Composable
fun DetailScreen(
    products: List<Product>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = Modifier.fillMaxSize()) {

        if (products.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // Translucent overlay
            )

            Column(
                modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.ProductCardSize * 2)
                        .clip(MaterialTheme.shapes.medium),
                    model = ImageRequest.Builder(LocalContext.current).data(products[0].ImgUrl).build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = products[0].productName)

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(products.size) { index ->
                        products[index].let { product ->
                            DetailedPrice(product = product)
                        }
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No product details available")
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailScreenPreview() {
    ForSalesTheme {
        DetailScreen(
            listOf(
                Product(
                    productID = 180,
                    companyName = "Carrefour",
                    productCategory = "ხორცი & ნახევარფაბრიკატები",
                    productName = "კუჭი ქათმის ქუალიკო 1 კგ",
                    productPrice = "7.95",
                    lastUpdated = "60",
                    companyImg = "1",
                    ImgUrl = "https://imageproxy.wolt.com/menu/menu-images/5f6b69ba5d07dca217bd3620/2383c440-f7e4-11ee-8413-cec83d668e87_289623.jpg"
                ),
                Product(
                    productID = 181,
                    companyName = "Magniti",
                    productCategory = "ხორცი & ნახევარფაბრიკატები",
                    productName = "კუჭი ქათმის ქუალიკო 1 კგ",
                    productPrice = "8.45",
                    lastUpdated = "15",
                    companyImg = "1",
                    ImgUrl = "https://imageproxy.wolt.com/menu/menu-images/5f6b69ba5d07dca217bd3620/2383c440-f7e4-11ee-8413-cec83d668e87_289623.jpg"
                )
            )
      )
    }
}