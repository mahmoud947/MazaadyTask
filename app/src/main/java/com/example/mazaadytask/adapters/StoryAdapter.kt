package com.example.mazaadytask.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mazaadytask.databinding.ItemSotryBinding

class StoryAdapter (private val interaction: Interaction? = null) :
    ListAdapter<Int, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Int>() {

            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean =
                oldItem != newItem

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean =
                oldItem != newItem

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemStoryViewHolder.from(parent, interaction = interaction)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemStoryViewHolder -> {
                val item = getItem(position)
                holder.onBind(item)
            }
        }
    }


    class ItemStoryViewHolder
    constructor(
        private val binding: ItemSotryBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Int) {
            binding.imageView.setImageResource(item)
            binding.root.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            binding.executePendingBindings()
        }


        companion object {
            fun from(viewGroup: ViewGroup, interaction: Interaction?): ItemStoryViewHolder {
                val bind = ItemSotryBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return ItemStoryViewHolder(bind, interaction = interaction)
            }
        }

    }


    interface Interaction {
        fun onItemSelected(position: Int, item: Int)
    }
}
