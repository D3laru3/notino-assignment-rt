package robert.tomas.notino.data.mapper

import robert.tomas.notino.data.remote.dto.VpProductById
import robert.tomas.notino.domain.model.Product

fun VpProductById.toProduct(): Product =
    Product(
        productId = this.productId,
        name = this.name,
        brandName = this.brand.name,
        description = this.annotation,
        imageUrl = this.imageUrl,
        price = "${this.price.value} ${this.price.currency}",
        score = this.reviewSummary.score
    )