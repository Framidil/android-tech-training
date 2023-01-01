package com.rst.paging_3

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.lang.Integer.max

class MyPagingSource : PagingSource<Int, String>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, String> {
        val page = params.key ?: 0
        val data = DB.getItems(page)
        return LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = if (page > DB.pageCount - 1) null else page + 1,
            itemsAfter = if (page > DB.pageCount - 1) 0 else 5,
        )
    }

    override val jumpingSupported: Boolean
        get() = true

    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        return state.anchorPosition
    }
}

class Adapter :
    PagingDataAdapter<String, Adapter.MyViewHolder>(object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            (oldItem === newItem)

        override fun areContentsTheSame(oldItem: String, newItem: String) = (oldItem == newItem)

    }) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        Log.wtf("TAG", "item: $item")
        if (item == null) {
            holder.bindPlaceholder()
        } else {
            holder.bind(item)
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tv = view.findViewById<TextView>(R.id.itemTextView)
        fun bind(s: String) {
            tv.text = s
        }

        fun bindPlaceholder() {
            tv.text = "PLACEHOLDER"
        }
    }
}