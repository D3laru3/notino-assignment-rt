package robert.tomas.notino.presentation.product_list

import robert.tomas.notino.domain.model.Product

/**
 * State of the product list.
 */
data class ProductsState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: Boolean = false,
    val favourite: Int? = null
)
