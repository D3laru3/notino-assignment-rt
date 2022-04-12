package robert.tomas.notino.presentation.product_list.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import robert.tomas.notino.R
import robert.tomas.notino.domain.model.Product

/**
 * Product item view.
 */
@Composable
fun ProductItem(
    product: Product,
    onFavouriteClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Image(
                painter = if (product.isFavourite) {
                    painterResource(id = R.drawable.ic_heart_fill)
                } else {
                    painterResource(
                        id = R.drawable.ic_heart_empty
                    )
                },
                contentDescription = "Favourite",
                modifier = Modifier
                    .clickable {
                        onFavouriteClicked.invoke(product.productId)
                    }
                    .size(24.dp)
            )
        }
        AsyncImage(
            modifier = Modifier.size(125.dp),
            model = "https://i.notino.com/detail_zoom/${product.imageUrl}",
            contentDescription = "${product.name} image"
        )
        Text(text = product.brandName, style = MaterialTheme.typography.subtitle1)
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = product.name, style = MaterialTheme.typography.subtitle2)
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = product.description, style = MaterialTheme.typography.caption)
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 1..5) {
                Image(
                    painter = painterResource(
                        id = if (product.score > i)
                            R.drawable.ic_star
                        else
                            R.drawable.ic_star_empty
                    ),
                    contentDescription = "rating star",
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = product.price)
        Spacer(modifier = Modifier.size(12.dp))
        OutlinedButton(
            onClick = { /*TODO*/ },
            Modifier.background(color = Color.Transparent)
        ) {
            Text(text = stringResource(id = R.string.product_list_add_product_to_cart))
        }
    }
}