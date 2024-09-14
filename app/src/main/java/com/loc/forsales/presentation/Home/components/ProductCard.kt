package com.loc.forsales.presentation.Home.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.loc.forsales.domain.model.Product
import com.loc.forsales.presentation.Dimens.ProductCardSize
import com.loc.forsales.ui.theme.ForSalesTheme


@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardWidth = (LocalContext.current.resources.displayMetrics.widthPixels / 3 - 24).dp

    Card(
        modifier = modifier
            .width(cardWidth)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Box {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.medium),
                    model = ImageRequest.Builder(LocalContext.current).data(product.ImgUrl).build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                            MaterialTheme.shapes.small
                        )
                        .padding(2.dp)
                ) {
                    Text(
                        text = product.lastUpdated + " საათის წინ",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 8.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.productName,
                textAlign = TextAlign.Left,
                maxLines = 2,
                //overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${product.productPrice} ₾",
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.weight(1f)
                )

                CompanyLogo(
                    companyName = product.companyName,
                    modifier = Modifier.size(width = 40.dp, height = 20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun Product() {
    ForSalesTheme(dynamicColor = false) {
        ProductCard(
            product = com.loc.forsales.domain.model.Product(
                productID = 180,
                companyName = "carrefour",
                productCategory = "ხორცი & ნახევარფაბრიკატები",
                productName = "კუჭი ქათმის ქუალიკო 1 კგ",
                productPrice = "7.95",
                lastUpdated = "60",
                ImgUrl = "https://imageproxy.wolt.com/menu/menu-images/5f6b69ba5d07dca217bd3620/2383c440-f7e4-11ee-8413-cec83d668e87_289623.jpg"
            ), onClick = {}
        )
    }
}
