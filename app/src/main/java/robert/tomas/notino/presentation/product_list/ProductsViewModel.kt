package robert.tomas.notino.presentation.product_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import robert.tomas.notino.domain.repository.ProductsRepository
import robert.tomas.notino.util.Resource

/**
 * View model for the list of products.
 */
@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {
    var state by mutableStateOf(ProductsState())

    init {
        getProducts()
    }

    fun onEvent(event: ProductsEvent) {
        when (event) {
            is ProductsEvent.OnFavouriteClicked -> {
                state = state.copy(favourite = event.id)
                setFavouriteProduct()
                getProducts()
            }
            ProductsEvent.Refresh -> {
                getProducts(true)
            }
        }
    }

    private fun setFavouriteProduct() {
        state.favourite?.let { favouriteId ->
            viewModelScope.launch {
                repository.setFavouriteProduct(favouriteId)
            }
        }
    }

    private fun getProducts(fromRemote: Boolean = false) {
        viewModelScope.launch {
            repository.getProducts(fromRemote).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        state = state.copy(error = true, isLoading = false)
                    }
                    is Resource.Loading -> state = state.copy(isLoading = result.isLoading, error = false)
                    is Resource.Success ->
                        result.data?.let {
                            state = state.copy(
                                products = it
                            )
                        }

                }
            }
        }
    }
}
