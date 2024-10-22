package com.example.mazaadytask.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.ResultModel
import com.example.mazaadytask.databinding.ItemResultBinding
import com.example.mazaadytask.databinding.ItemSelectTextBinding

class ResultAdapter (private val interaction: Interaction? = null) :
    ListAdapter<ResultModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultModel>() {

            override fun areItemsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean =
                oldItem.key == newItem.key

            override fun areContentsTheSame(oldItem: ResultModel, newItem: ResultModel): Boolean =
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
        private val binding: ItemResultBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ResultModel) {
            binding.tvKey.text = item.key + ":"
            binding.tvValue.text = item.value
            binding.root.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            binding.executePendingBindings()
        }


        companion object {
            fun from(viewGroup: ViewGroup, interaction: Interaction?): ItemSelectViewHolder {
                val bind = ItemResultBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return ItemSelectViewHolder(bind, interaction = interaction)
            }
        }

    }


    interface Interaction {
        fun onItemSelected(position: Int, item: ResultModel)
    }
}
