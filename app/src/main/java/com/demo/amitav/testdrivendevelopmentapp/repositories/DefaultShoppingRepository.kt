package com.demo.amitav.testdrivendevelopmentapp.repositories

import androidx.lifecycle.LiveData
import com.demo.amitav.testdrivendevelopmentapp.data.local.ShoppingDao
import com.demo.amitav.testdrivendevelopmentapp.data.local.ShoppingItem
import com.demo.amitav.testdrivendevelopmentapp.data.remote.PixabayAPI
import com.demo.amitav.testdrivendevelopmentapp.data.remote.responses.ImageResponse
import com.demo.amitav.testdrivendevelopmentapp.other.Resource
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
) : ShoppingRepository {

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if(response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error("An unknown error occured", null)
            } else {
                Resource.Error("An unknown error occured", null)
            }
        } catch(e: Exception) {
            Resource.Error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}














