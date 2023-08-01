package com.example.shoppinglistapp.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.presentation.viewmodel.ShopItemViewModel
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var tilShopItemName: TextInputLayout
    private lateinit var tilShopItemCount: TextInputLayout
    private lateinit var etShopItemName: EditText
    private lateinit var etShopItemCount: EditText
    private lateinit var btnSaveShopItem: Button

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var onEditingFinishListener: OnEditingFinishListener

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishListener) {
            onEditingFinishListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
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
        viewModel.shopItem.observe(viewLifecycleOwner) {
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
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            tilShopItemName.error = if (it) {
                getString(R.string.name_input_error)
            } else {
                null
            }
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            tilShopItemCount.error = if (it) {
                getString(R.string.count_input_error)
            } else {
                null
            }
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishListener.onEditingFinished()
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

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Screen extra mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode: $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Screen extra shop item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID)
        }
    }

    private fun initViews(view: View) {
        tilShopItemName = view.findViewById(R.id.til_shop_item_name)
        tilShopItemCount = view.findViewById(R.id.til_shop_item_count)
        etShopItemName = view.findViewById(R.id.et_shop_item_name)
        etShopItemCount = view.findViewById(R.id.et_shop_item_count)
        btnSaveShopItem = view.findViewById(R.id.btn_save_shop_item)
    }

    interface OnEditingFinishListener {
        fun onEditingFinished()
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = "mode_unknown"

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}