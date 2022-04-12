package robert.tomas.notino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import dagger.hilt.android.AndroidEntryPoint
import robert.tomas.notino.presentation.product_list.ui.ProductsGrid
import robert.tomas.notino.ui.theme.NotinoTheme
import robert.tomas.notino.ui.theme.ToolbarBackgroundColor

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotinoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Scaffold(topBar = {
                        TopAppBar(
                            content = {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(id = R.string.app_name),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h6
                                )
                            },
                            backgroundColor = ToolbarBackgroundColor,
                        )
                    }) {
                        ProductsGrid()
                    }
                }
            }
        }
    }
}