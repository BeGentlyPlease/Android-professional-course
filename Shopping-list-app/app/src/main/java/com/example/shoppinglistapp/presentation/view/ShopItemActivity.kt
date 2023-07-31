package com.example.shoppinglistapp.presentation.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.presentation.viewmodel.ShopItemViewModel
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var tilShopItemName: TextInputLayout
    private lateinit var tilShopItemCount: TextInputLayout
    private lateinit var etShopItemName: EditText
    private lateinit var etShopItemCount: EditText
    private lateinit var btnSaveShopItem: Button

    private lateinit var viewModel: ShopItemViewModel

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        initViews()
        addTextChangeListeners()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        launchMode()
        observeViewModel()
    }

    private fun launchMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun launchAddMode() {
        btnSaveShopItem.setOnClickListener {
            viewModel.addShopItem(
                etShopItemName.text.toString(),
                etShopItemCount.text.toString()
            )
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this) {
            etShopItemName.setText(it.name)
            etShopItemCount.setText(it.count.toString())
        }
        btnSaveShopItem.setOnClickListener {
            viewModel.updateShopItem(
                etShopItemName.text.toString(),
                etShopItemCount.text.toString()
            )
        }
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(this) {
            tilShopItemName.error = if (it) {
                getString(R.string.name_input_error)
            } else {
                null
            }
        }
        viewModel.errorInputCount.observe(this) {
            tilShopItemCount.error = if (it) {
                getString(R.string.count_input_error)
            } else {
                null
            }
        }
        viewModel.shouldCloseScreen.observe(this) {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun addTextChangeListeners() {
        etShopItemName.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputName()
        }
        etShopItemCount.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputCount()
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_MODE)) {
            throw RuntimeException("Screen extra mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode: $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Screen extra shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews() {
        tilShopItemName = findViewById(R.id.til_shop_item_name)
        tilShopItemCount = findViewById(R.id.til_shop_item_count)
        etShopItemName = findViewById(R.id.et_shop_item_name)
        etShopItemCount = findViewById(R.id.et_shop_item_count)
        btnSaveShopItem = findViewById(R.id.btn_save_shop_item)
    }

    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = "mode_unknown"

        fun newIntentAddShopItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditShopItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}