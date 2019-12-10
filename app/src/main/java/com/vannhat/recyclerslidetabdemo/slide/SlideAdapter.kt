package com.vannhat.recyclerslidetabdemo.slide

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.vannhat.recyclerslidetabdemo.R

class SlideAdapter : RecyclerView.Adapter<SlideAdapter.SliderItemViewHolder>() {

    private val dataList = mutableListOf<String>()
    var callback: Callback? = null

    fun setData(data: MutableList<String>) {
        this.dataList.clear()
        this.dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderItemViewHolder {
        return SliderItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_slider_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: SliderItemViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            callback?.onItemClicked(position)
        }
        holder.ivAvatar?.let {
            Glide.with(holder.itemView.context)
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5v0bZMlrre3oLBxApjpqMJk_CXUCokvb2cx9fL5Z1c_FpoP0zRw&s")
                .apply(
                    RequestOptions.bitmapTransform(CircleCrop()).placeholder(
                        R.drawable.ic_launcher_background
                    )
                )
                .into(it)
        }
    }

    class SliderItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView? = itemView.findViewById(R.id.iv_avatar)
    }

    interface Callback {
        fun onItemClicked(position: Int)
    }
}
