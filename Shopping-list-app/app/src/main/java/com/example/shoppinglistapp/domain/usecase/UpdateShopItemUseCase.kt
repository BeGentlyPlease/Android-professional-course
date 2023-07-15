package com.example.shoppinglistapp.domain.usecase

import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.domain.repository.ShopItemRepository

class UpdateShopItemUseCase(shopItemRepository: ShopItemRepository) : ShopItemUseCase(
    shopItemRepository
) {
    fun updateShopItemUseCase(item: ShopItem) {
        shopItemRepository.updateShopItemUseCase(item)
    }
}