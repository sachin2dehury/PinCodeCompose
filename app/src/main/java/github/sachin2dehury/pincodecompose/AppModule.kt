package github.sachin2dehury.pincodecompose

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(moshiConverterFactory: MoshiConverterFactory) = Retrofit.Builder()
        .baseUrl(PinService.BASE_URL)
        .addConverterFactory(moshiConverterFactory)
        .build()

    @Provides
    @Singleton
    fun provideMoshi() = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi) = MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit) = retrofit.create(PinService::class.java)

    @Provides
    @Singleton
    fun provideRepository(service: PinService) = PinRepository(service)
}
