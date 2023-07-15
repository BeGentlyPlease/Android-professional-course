package com.example.shoppinglistapp.domain.usecase

import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.domain.repository.ShopItemRepository

class GetShopItemList(shopItemRepository: ShopItemRepository) : ShopItemUseCase(shopItemRepository) {
    fun getShopItemList(): List<ShopItem> {
        return shopItemRepository.getShopItemList()
    }
}