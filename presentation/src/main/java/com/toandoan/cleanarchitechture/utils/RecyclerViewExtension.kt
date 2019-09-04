package com.toandoan.cleanarchitechture.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addTouchHelper(callback: ItemTouchHelper.Callback) {
    val helper = ItemTouchHelper(callback)
    helper.attachToRecyclerView(this)
}