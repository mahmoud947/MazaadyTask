package com.example.mazaadytask.dialogs.model_bottom_sheet

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.BottomSheetItem
import com.example.mazaadytask.databinding.ItemSelectTextBinding

class BottomSheetAdapter (private val interaction: Interaction? = null) :
    ListAdapter<BottomSheetItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BottomSheetItem>() {

            override fun areItemsTheSame(oldItem: BottomSheetItem, newItem: BottomSheetItem): Boolean =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: BottomSheetItem, newItem: BottomSheetItem): Boolean =
                oldItem == newItem

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemSelectViewHolder.from(parent, interaction = interaction)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemSelectViewHolder -> {
                val item = getItem(position)
                holder.onBind(item)
            }
        }
    }


    class ItemSelectViewHolder
    constructor(
        private val binding: ItemSelectTextBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: BottomSheetItem) {
            binding.tvSlug.text = item.name
            binding.root.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            binding.executePendingBindings()
        }


        companion object {
            fun from(viewGroup: ViewGroup, interaction: Interaction?): ItemSelectViewHolder {
                val bind = ItemSelectTextBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return ItemSelectViewHolder(bind, interaction = interaction)
            }
        }

    }


    interface Interaction {
        fun onItemSelected(position: Int, item: BottomSheetItem)
    }
}
