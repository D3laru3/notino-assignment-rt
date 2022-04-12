package robert.tomas.notino.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import robert.tomas.notino.data.remote.ProductsApi

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create(
        GsonBuilder().setLenient().serializeNulls().setPrettyPrinting().create()
    )

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        loggingInterceptor: HttpLoggingInterceptor
    ): Retrofit =
        Retrofit
            .Builder()
            .addConverterFactory(gsonConverterFactory)
            .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
            .baseUrl(BASE_API_URL)
            .build()

    @Singleton
    @Provides
    fun provideProductsApi(retrofit: Retrofit): ProductsApi = retrofit.create(ProductsApi::class.java)

    companion object {
        const val BASE_API_URL = "https://my-json-server.typicode.com/cernfr1993/notino-assignment/db/"
    }
}