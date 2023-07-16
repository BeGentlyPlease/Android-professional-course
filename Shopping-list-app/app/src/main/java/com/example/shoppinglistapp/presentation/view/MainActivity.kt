package com.example.shoppinglistapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.domain.entity.ShopItem
import com.example.shoppinglistapp.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopItemListLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shopItemListLayout = findViewById(R.id.ll_shop_item_list)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java].apply {
            shopItemList.observe(this@MainActivity) {
                displayList(it)
            }
        }
    }

    private fun displayList(items: List<ShopItem>) {
        shopItemListLayout.removeAllViews()
        for (item in items) {
            val layoutId = if (item.enabled) {
                R.layout.shop_item_enabled
            } else {
                R.layout.shop_item_disabled
            }
            val view = LayoutInflater
                .from(this)
                .inflate(layoutId, shopItemListLayout, false)

            val tvName = view.findViewById<TextView>(R.id.tv_shop_item_name)
            val tvCount = view.findViewById<TextView>(R.id.tv_shop_item_count)

            tvName.text = item.name
            tvCount.text = item.count.toString()

            view.setOnLongClickListener {
                viewModel.changeShopItemEnableState(item)
                true
            }

            shopItemListLayout.addView(view)
        }

    }
}