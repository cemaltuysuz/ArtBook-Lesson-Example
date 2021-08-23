package com.thicapps.artbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.thicapps.artbook.R
import com.thicapps.artbook.room.Art
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(
   val glide:RequestManager
) : RecyclerView.Adapter<ImageRecyclerAdapter.ImageHolder>() {

    private var onItemClickListener :((String) -> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)
    var images : List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    class ImageHolder(view:View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_row,parent,false)
        return ImageHolder(view)
    }

    fun setOnClickListener(listener : (String) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val imageView = holder.itemView.findViewById<AppCompatImageView>(R.id.images_row_image)
        val currentPos = images[position]
        glide.load(currentPos).into(imageView)

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(currentPos)
            }
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}