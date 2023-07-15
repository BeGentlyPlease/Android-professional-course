package com.example.shoppinglistapp.data

import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.domain.repository.ShopItemRepository

object ShopItemRepositoryImpl : ShopItemRepository {

    private val shopItems = mutableListOf<ShopItem>()

    override fun addShopItem(item: ShopItem) {
        TODO("Not yet implemented")
    }

    override fun deleteShopItem(item: ShopItem) {
        TODO("Not yet implemented")
    }

    override fun getShopItemList(): List<ShopItem> {
        TODO("Not yet implemented")
    }

    override fun getShopItem(id: Int): ShopItem {
        TODO("Not yet implemented")
    }

    override fun updateShopItemUseCase(item: ShopItem) {
        TODO("Not yet implemented")
    }


}