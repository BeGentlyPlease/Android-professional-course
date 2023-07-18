package com.example.shoppinglistapp.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R

class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvName: TextView = view.findViewById(R.id.tv_shop_item_name)
    val tvCount: TextView = view.findViewById(R.id.tv_shop_item_count)
}