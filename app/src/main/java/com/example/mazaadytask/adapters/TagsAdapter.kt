package com.example.mazaadytask.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mazaadytask.databinding.ItemSotryBinding
import com.example.mazaadytask.databinding.ItemTagBinding
class TagsAdapter(private val interaction: Interaction? = null) :
    ListAdapter<String, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {

            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TagsAdapterViewHolder.from(parent, interaction = interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TagsAdapterViewHolder -> {
                val item = getItem(position)
                holder.onBind(item, isSelected = position == selectedItemPosition)
            }
        }
    }

    fun selectItem(position: Int) {
        val previousItemPosition = selectedItemPosition
        selectedItemPosition = position

        notifyItemChanged(previousItemPosition)
        notifyItemChanged(selectedItemPosition)
    }

    class TagsAdapterViewHolder
    constructor(
        private val binding: ItemTagBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: String, isSelected: Boolean) {
            binding.root.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            binding.tvTag.text = item

            if (isSelected) {
                binding.cvTag.setCardBackgroundColor(Color.parseColor("#EC5F5F"))
                binding.tvTag.setTextColor(Color.parseColor("#FFFFFF"))
            } else {
                binding.cvTag.setCardBackgroundColor(Color.parseColor("#F6F7FA"))
                binding.tvTag.setTextColor(Color.parseColor("#9D9FA0"))
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(viewGroup: ViewGroup, interaction: Interaction?): TagsAdapterViewHolder {
                val bind = ItemTagBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return TagsAdapterViewHolder(bind, interaction = interaction)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: String)
    }
}