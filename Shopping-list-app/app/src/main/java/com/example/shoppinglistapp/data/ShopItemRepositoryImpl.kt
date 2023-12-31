package com.example.shoppinglistapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.domain.repository.ShopItemRepository

object ShopItemRepositoryImpl : ShopItemRepository {

    private val shopItemsLiveData = MutableLiveData<List<ShopItem>>()

    private val shopItems = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private var shopItemAutoIncrementId = 0

    init {
        for (i in 0..10) {
            addShopItem(ShopItem(i.toString(), i.toFloat(), true))
        }
    }

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