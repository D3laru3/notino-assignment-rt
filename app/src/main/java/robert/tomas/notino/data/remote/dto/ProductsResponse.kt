package robert.tomas.notino.data.remote.dto

/**
 * Remote response for products.
 */
data class ProductsResponse(
    val vpProductByIds: List<VpProductById>
)

/**
 * Remote response product by id.
 */
data class VpProductById(
    val productId: Int,
    val `annotation`: String,
    val brand: Brand,
    val imageUrl: String,
    val name: String,
    val reviewSummary: ReviewSummary,
    val price: Price
)

/**
 * Remote response product brand.
 */
data class Brand(
    val name: String
)

/**
 * Remote response product price.
 */
data class Price(
    val currency: String,
    val value: Int
)

/**
 * Remote response product review summary.
 */
data class ReviewSummary(
    val averageRating: Double,
    val score: Int
)
