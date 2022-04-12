package robert.tomas.notino.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import robert.tomas.notino.data.repository.ProductsRepositoryImpl
import robert.tomas.notino.domain.repository.ProductsRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    /**
     * Provide products repository.
     */
    @Binds
    @Singleton
    abstract fun bindProductsRepository(productsRepositoryImpl: ProductsRepositoryImpl): ProductsRepository
}