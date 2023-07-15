package com.example.shoppinglistapp.data

import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.domain.repository.ShopItemRepository
import java.lang.RuntimeException

object ShopItemRepositoryImpl : ShopItemRepository {

    private val shopItems = mutableListOf<ShopItem>()

    private var shopItemAutoIncrementId = 0

    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID) {
            item.id = shopItemAutoIncrementId++
        }
        shopItems.add(item)
    }

    override fun deleteShopItem(item: ShopItem) {
        shopItems.remove(item)
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