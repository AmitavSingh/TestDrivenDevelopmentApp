package com.demo.amitav.testdrivendevelopmentapp.repositories

import androidx.lifecycle.LiveData
import com.demo.amitav.testdrivendevelopmentapp.data.local.ShoppingItem
import com.demo.amitav.testdrivendevelopmentapp.data.remote.responses.ImageResponse
import com.demo.amitav.testdrivendevelopmentapp.other.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}