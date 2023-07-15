package com.example.shoppinglistapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.shoppinglistapp.domain.entity.ShopItem

interface ShopItemRepository {
    fun addShopItem(item: ShopItem)

    fun deleteShopItem(item: ShopItem)

    fun getShopItemList(): LiveData<List<ShopItem>>

    fun getShopItem(id: Int): ShopItem

    fun updateShopItemUseCase(item: ShopItem)
}