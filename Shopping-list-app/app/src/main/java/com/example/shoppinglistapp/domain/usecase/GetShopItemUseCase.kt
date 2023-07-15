package com.example.shoppinglistapp.domain.usecase

import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.domain.repository.ShopItemRepository

class GetShopItemUseCase(shopItemRepository: ShopItemRepository) : ShopItemUseCase(
    shopItemRepository
) {
    fun getShopItem(id: Int): ShopItem {
        return shopItemRepository.getShopItem(id)
    }
}