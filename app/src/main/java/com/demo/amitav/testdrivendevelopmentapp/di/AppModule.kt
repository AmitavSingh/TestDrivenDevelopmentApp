package com.demo.amitav.testdrivendevelopmentapp.di

import android.content.Context
import androidx.room.Room
import com.demo.amitav.testdrivendevelopmentapp.data.local.ShoppingDao
import com.demo.amitav.testdrivendevelopmentapp.data.local.ShoppingItemDatabase
import com.demo.amitav.testdrivendevelopmentapp.data.remote.PixabayAPI
import com.demo.amitav.testdrivendevelopmentapp.other.Constants.BASE_URL
import com.demo.amitav.testdrivendevelopmentapp.other.Constants.DATABASE_NAME
import com.demo.amitav.testdrivendevelopmentapp.repositories.DefaultShoppingRepository
import com.demo.amitav.testdrivendevelopmentapp.repositories.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository


    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }
}

















