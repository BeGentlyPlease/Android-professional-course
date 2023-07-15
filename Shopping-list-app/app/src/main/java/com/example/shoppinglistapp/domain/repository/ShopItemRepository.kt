package com.example.shoppinglistapp.domain.repository

import com.example.shoppinglistapp.domain.entity.ShopItem

interface ShopItemRepository {
    fun addShopItem(item: ShopItem)

    fun deleteShopItem(item: ShopItem)

    fun getShopItemList(): List<ShopItem>

    fun getShopItem(id: Int): ShopItem

    fun updateShopItemUseCase(item: ShopItem)
}