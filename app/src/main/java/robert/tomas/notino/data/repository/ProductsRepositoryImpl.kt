package robert.tomas.notino.data.repository

import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import robert.tomas.notino.data.mapper.toProduct
import robert.tomas.notino.data.remote.ProductsApi
import robert.tomas.notino.domain.model.Product
import robert.tomas.notino.domain.repository.ProductsRepository
import robert.tomas.notino.util.Resource

/**
 * Implementation of the products repository from domain layer.
 */
@Singleton
class ProductsRepositoryImpl @Inject constructor(
    private val productsApi: ProductsApi
) : ProductsRepository {
    // Could be and should be cached directly in database.
    private var cachedProducts = mutableMapOf<Int, Product>()

    override suspend fun getProducts(fromRemote: Boolean): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())

        // There we could call the database and cached data and emit [Resource.Success(dataFromDb)].
        emit(Resource.Success(cachedProducts.values.toList()))

        val shouldLoadFromCache = cachedProducts.isNotEmpty() && !fromRemote
        if (shouldLoadFromCache) {
            emit(Resource.Loading(false))
            return@flow
        }

        val remoteProducts = try {
            val response = productsApi.getProducts()
            val remoteProducts = response.vpProductByIds.map {
                it.toProduct()
            }
            val favouriteProducts = cachedProducts.filter { it.value.isFavourite }
            val productsMap = mutableMapOf<Int, Product>()
            remoteProducts.associateByTo(productsMap) {
                it.productId
            }
            favouriteProducts.forEach {
                productsMap[it.value.productId]?.isFavourite = true
            }
            cachedProducts = productsMap
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't load data from remote"))
            null
        } catch (e: HttpException) {
            emit(Resource.Error("Couldn't load data from remote"))
            null
        }
        remoteProducts?.let {
            emit(Resource.Success(cachedProducts.values.toList()))
        }
    }

    override suspend fun setFavouriteProduct(id: Int) {
        cachedProducts[id]?.isFavourite = true
    }
}