package com.example.shoppinglistapp.data

import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.domain.repository.ShopItemRepository
import java.lang.RuntimeException

object ShopItemRepositoryImpl : ShopItemRepository {

    private val shopItems = mutableListOf<ShopItem>()

    override fun addShopItem(item: ShopItem) {
        TODO("Not yet implemented")
    }

    override fun deleteShopItem(item: ShopItem) {
        TODO("Not yet implemented")
    }

    override fun getShopItemList(): List<ShopItem> {
        return shopItems.toList()
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopItems.find {
            it.id == id
        } ?: throw RuntimeException("Shop item with id: $id not found!")
    }

    override fun updateShopItemUseCase(item: ShopItem) {
        TODO("Not yet implemented")
    }


}