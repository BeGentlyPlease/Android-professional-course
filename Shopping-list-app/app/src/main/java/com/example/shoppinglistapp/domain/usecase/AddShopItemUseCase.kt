package com.example.shoppinglistapp.domain.usecase

import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.domain.repository.ShopItemRepository

class AddShopItemUseCase(shopItemRepository: ShopItemRepository) : ShopItemUseCase(
    shopItemRepository
) {
    fun addShopItem(item: ShopItem) {
        shopItemRepository.addShopItem(item)
    }
}