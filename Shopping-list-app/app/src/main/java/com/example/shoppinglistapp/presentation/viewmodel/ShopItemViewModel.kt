package com.example.shoppinglistapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglistapp.data.ShopItemRepositoryImpl
import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.domain.usecase.AddShopItemUseCase
import com.example.shoppinglistapp.domain.usecase.GetShopItemUseCase
import com.example.shoppinglistapp.domain.usecase.UpdateShopItemUseCase

class ShopItemViewModel : ViewModel() {

    private val shopItemRepository = ShopItemRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(shopItemRepository)
    private val addShopItemUseCase = AddShopItemUseCase(shopItemRepository)
    private val updateShopItemUseCase = UpdateShopItemUseCase(shopItemRepository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(id: Int) {
        val item = getShopItemUseCase.getShopItem(id)
        _shopItem.value = item
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }
    }

    fun updateShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItem.value?.let { oldShopItem ->
                val newShopItem = oldShopItem.copy(name = name, count = count)
                updateShopItemUseCase.updateShopItemUseCase(newShopItem)
                finishWork()
            }
        }
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun validateInput(inputName: String, inputCount: Float): Boolean {
        var result = true
        if (inputName.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (inputCount <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Float {
        return try {
            inputCount?.trim()?.toFloat() ?: 0f
        } catch (e: Exception) {
            0f
        }
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}