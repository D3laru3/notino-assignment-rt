package robert.tomas.notino.presentation.product_list

/**
 * Events that could be triggered from ui.
 */
sealed class ProductsEvent {
    object Refresh: ProductsEvent()
    data class OnFavouriteClicked(val id: Int) : ProductsEvent()
}
