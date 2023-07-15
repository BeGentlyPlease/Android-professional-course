package com.example.shoppinglistapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.domain.repository.ShopItemRepository

class GetShopItemListUseCase(shopItemRepository: ShopItemRepository) :
    ShopItemUseCase(shopItemRepository) {
    fun getShopItemList(): LiveData<List<ShopItem>> {
        return shopItemRepository.getShopItemList()
    }
}