package com.example.shoppinglistapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shoppinglistapp.data.ShopItemRepositoryImpl
import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.domain.usecase.DeleteShopItemUseCase
import com.example.shoppinglistapp.domain.usecase.GetShopItemListUseCase
import com.example.shoppinglistapp.domain.usecase.UpdateShopItemUseCase

class MainViewModel : ViewModel() {

    private val shopItemRepository = ShopItemRepositoryImpl

    private val getShopItemListUseCase = GetShopItemListUseCase(shopItemRepository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(shopItemRepository)
    private val updateShopItemUseCase = UpdateShopItemUseCase(shopItemRepository)

    val shopItemList = getShopItemListUseCase.getShopItemList()

    fun deleteShopItem(item: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(item)
    }

    fun changeShopItemEnableState(item: ShopItem) {
        val newItem = item.copy(enabled = !item.enabled)
        updateShopItemUseCase.updateShopItemUseCase(newItem)
    }
}