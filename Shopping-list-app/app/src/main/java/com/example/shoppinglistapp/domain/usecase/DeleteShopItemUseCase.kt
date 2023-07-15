package com.example.shoppinglistapp.domain.usecase

import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.domain.repository.ShopItemRepository

class DeleteShopItemUseCase(
    shopItemRepository: ShopItemRepository
) : ShopItemUseCase(shopItemRepository) {
    fun deleteShopItem(item: ShopItem) {
        shopItemRepository.deleteShopItem(item)
    }
}