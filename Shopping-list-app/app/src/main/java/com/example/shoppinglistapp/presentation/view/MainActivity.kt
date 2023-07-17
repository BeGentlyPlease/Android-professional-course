package com.example.shoppinglistapp.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.presentation.adapter.ShopItemListAdapter
import com.example.shoppinglistapp.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopItemListAdapter: ShopItemListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java].apply {
            shopItemList.observe(this@MainActivity) {
                shopItemListAdapter.shopItemList = it
            }
        }
    }

    private fun setupRecyclerView() {
        val rvShopItemList = findViewById<RecyclerView>(R.id.rv_shop_item_list)
        with(rvShopItemList) {
            shopItemListAdapter = ShopItemListAdapter()
            adapter = shopItemListAdapter
        }
    }

}