package robert.tomas.notino.presentation.product_list.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import robert.tomas.notino.R
import robert.tomas.notino.presentation.product_list.ProductsEvent
import robert.tomas.notino.presentation.product_list.ProductsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductsGrid(
    modifier: Modifier = Modifier,
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val state = viewModel.state

    val (showSnackBar, setShowSnackBar) = remember {
        mutableStateOf(false)
    }

    if (showSnackBar) {
        LaunchedEffect(key1 = true) {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = context.getString(
                    R.string
                        .product_list_unable_to_load_products_error_text
                )
            )
            when (result) {
                SnackbarResult.Dismissed -> setShowSnackBar(false)
                SnackbarResult.ActionPerformed -> setShowSnackBar(false)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState
    ) {
        if (state.error) {
            setShowSnackBar(true)
            viewModel.state = state.copy(error = false)
        }
        SwipeRefresh(
            state = swipeRefreshState, onRefresh = {
                viewModel.onEvent(ProductsEvent.Refresh)
            }) {
            if (state.products.isNotEmpty()) {
                LazyVerticalGrid(
                    contentPadding = PaddingValues(12.dp),
                    cells = GridCells.Fixed(2), content = {
                        items(state.products) { product ->
                            ProductItem(product = product, {
                                viewModel.onEvent(ProductsEvent.OnFavouriteClicked(it))
                            })
                        }
                    })
            } else {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Text(
                            text = stringResource(id = R.string.product_list_empty_list_info),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}