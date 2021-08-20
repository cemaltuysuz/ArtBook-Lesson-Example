package com.thicapps.artbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.thicapps.artbook.R
import com.thicapps.artbook.room.Art
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    val glide:RequestManager
) : RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Art>(){
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)
    var arts : List<Art>
    get() = recyclerListDiffer.currentList
    set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.art_row,parent,false)
        return ArtViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val currentPosition = arts[position]

        holder.artName.text       = currentPosition.name
        holder.artistName.text    = currentPosition.artistName
        holder.artYear.text       = currentPosition.year.toString()
        glide.load(currentPosition.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    class ArtViewHolder(view:View) :RecyclerView.ViewHolder(view){
        val image:AppCompatImageView       = view.findViewById(R.id.artImage)
        val artName: AppCompatTextView     = view.findViewById(R.id.priv_row_art_name)
        val artistName:AppCompatTextView   = view.findViewById(R.id.priv_row_artist_name)
        val artYear:AppCompatTextView      = view.findViewById(R.id.priv_row_art_year)
    }
}