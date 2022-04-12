package robert.tomas.notino.data.remote

import retrofit2.http.GET
import robert.tomas.notino.data.remote.dto.ProductsResponse

/**
 * Remote api for products.
 */
interface ProductsApi {

    /**
     * Get products.
     * Get link not needed as the base url is the only one url that we will use in this project.
     */
    @GET(".")
    suspend fun getProducts(): ProductsResponse
}