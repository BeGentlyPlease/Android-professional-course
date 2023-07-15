package com.example.shoppinglistapp.domain.usecase

import com.example.shoppinglistapp.domain.repository.ShopItemRepository

abstract class ShopItemUseCase(protected val shopItemRepository: ShopItemRepository)