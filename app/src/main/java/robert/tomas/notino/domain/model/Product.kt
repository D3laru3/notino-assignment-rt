package robert.tomas.notino.domain.model

data class Product(
    val productId: Int,
    val description: String,
    val brandName: String,
    val imageUrl: String,
    val name: String,
    val score: Int,
    val price: String,
    var isFavourite: Boolean = false
)
