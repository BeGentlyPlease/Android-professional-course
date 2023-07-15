package com.example.shoppinglistapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.domain.repository.ShopItemRepository

object ShopItemRepositoryImpl : ShopItemRepository {

    private val shopItemsLiveData = MutableLiveData<List<ShopItem>>()

    private val shopItems = mutableListOf<ShopItem>()

    private var shopItemAutoIncrementId = 0

    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID) {
            item.id = shopItemAutoIncrementId++
        }
        shopItems.add(item)
        updateShopItemsLiveData()
    }

    override fun deleteShopItem(item: ShopItem) {
        shopItems.remove(item)
        updateShopItemsLiveData()
    }

    override fun getShopItemList(): LiveData<List<ShopItem>> {
        return shopItemsLiveData
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopItems.find {
            it.id == id
        } ?: throw RuntimeException("Shop item with id: $id not found!")
    }

    override fun updateShopItemUseCase(item: ShopItem) {
        val oldElement = getShopItem(item.id)
        shopItems.remove(oldElement)
        addShopItem(item)
    }

    private fun updateShopItemsLiveData() {
        shopItemsLiveData.value = shopItems.toList()
    }
}