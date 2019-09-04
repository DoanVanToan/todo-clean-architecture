package com.toandoan.cleanarchitechture.base

import android.view.View
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

abstract class BaseAdapter<T, VH : BaseViewHolder>(
    callback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(
    AsyncDifferConfig.Builder<T>(callback)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
)

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view)