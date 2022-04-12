package robert.tomas.notino.domain.repository

import kotlinx.coroutines.flow.Flow
import robert.tomas.notino.domain.model.Product
import robert.tomas.notino.util.Resource

/**
 * Product repository abstraction to hide the data implementation. Presentation layer should access only this
 * abstraction not the actual implementation of the data layer.
 */
interface ProductsRepository {
    /**
     * Get products.
     */
    suspend fun getProducts(fromRemote: Boolean = false): Flow<Resource<List<Product>>>

    /**
     * Set favourite product.
     */
    suspend fun setFavouriteProduct(id: Int)
}