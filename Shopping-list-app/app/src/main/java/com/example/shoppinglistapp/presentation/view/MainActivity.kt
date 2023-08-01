package com.example.shoppinglistapp.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.presentation.adapter.ShopItemListAdapter
import com.example.shoppinglistapp.presentation.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopItemListAdapter: ShopItemListAdapter

    private var shopItemFragmentContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopItemFragmentContainer = findViewById(R.id.shop_item_container)
        setupRecyclerView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java].apply {
            shopItemList.observe(this@MainActivity) {
                shopItemListAdapter.submitList(it)
            }
        }

        val buttonAddHopItem = findViewById<FloatingActionButton>(R.id.fab_add_shop_item)
        buttonAddHopItem.setOnClickListener {
            if (isOnePanMode()) {
                val intent = ShopItemActivity.newIntentAddShopItem(this@MainActivity)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun launchFragment(fragment: ShopItemFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isOnePanMode(): Boolean {
        return shopItemFragmentContainer == null
    }

    private fun setupRecyclerView() {
        shopItemListAdapter = ShopItemListAdapter().apply {
            onItemClickListener = { shopItem ->
                if (isOnePanMode()) {
                    val intent =
                        ShopItemActivity.newIntentEditShopItem(this@MainActivity, shopItem.id)
                    startActivity(intent)
                } else {
                    launchFragment(ShopItemFragment.newInstanceEditItem(shopItem.id))
                }
            }
            onItemLongClickListener = {
                viewModel.changeShopItemEnableState(it)
            }
        }

        findViewById<RecyclerView>(R.id.rv_shop_item_list).apply {
            adapter = shopItemListAdapter
            setupSwipeListener(this)
        }
    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shopItem = shopItemListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(shopItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}