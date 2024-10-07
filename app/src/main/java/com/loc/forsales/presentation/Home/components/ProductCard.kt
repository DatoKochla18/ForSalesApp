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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.loc.forsales.R
import com.loc.forsales.domain.model.Product
import com.loc.forsales.presentation.Dimens.ProductCardSize
import com.loc.forsales.ui.theme.ForSalesTheme
import java.util.Date
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.Duration

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit
@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    onSelectionChange: (Int) -> Unit, // Notify ViewModel of selection
    modifier: Modifier = Modifier
) {
    val cardWidth = (LocalContext.current.resources.displayMetrics.widthPixels / 3 - 24).dp
    var isButtonClicked by remember { mutableStateOf(false) }

    // Remove the fractional seconds and the 'Z' timezone designator from the date string
    val simplifiedDateString = product.lastUpdated.substringBefore(".") + "Z"

    // Updated date format to parse the simplified date
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC") // Set the time zone to UTC

    // Calculate hours difference
    val lastUpdatedDate = dateFormat.parse(simplifiedDateString) ?: Date()
    val currentDate = Date()
    val differenceInMillis = currentDate.time - lastUpdatedDate.time
    val hoursDifference = TimeUnit.MILLISECONDS.toHours(differenceInMillis)

    Card(
        modifier = modifier
            .width(cardWidth)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
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

                // Top-left corner button
                IconButton(
                    onClick = {
                        isButtonClicked = !isButtonClicked
                        onSelectionChange(product.productID) // Notify selection change
                    },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "Add",
                        tint = if (isButtonClicked) Color.Green else Color.Black
                    )
                }

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
                        text = "$hoursDifference საათის წინ", // Display hours difference
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
                    product.companyImg,
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
                companyImg = "123",
                ImgUrl = "https://imageproxy.wolt.com/menu/menu-images/5f6b69ba5d07dca217bd3620/2383c440-f7e4-11ee-8413-cec83d668e87_289623.jpg"
            ), onClick = {}, onSelectionChange = {}
        )
    }
}
