package com.fadlan.palindromeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.fadlan.palindromeapp.data.UserData
import com.fadlan.palindromeapp.databinding.ItemUserBinding

class UserAdapter : PagingDataAdapter<UserData, UserAdapter.UserViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
        holder.itemView.setOnClickListener {
            if (data != null) {
                onItemClickCallback.onItemClicked("${data.firstName} ${data.lastName}")
            }
        }
    }

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        onItemClickCallback = callback
    }

    interface OnItemClickCallback {
        fun onItemClicked(name: String)
    }

    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserData) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(imageView)
                firstName.text = user.firstName
                lastName.text = user.lastName
                email.text = user.email
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserData>() {
            override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
                return oldItem.id == newItem.id // Compare by ID for item sameness
            }

            override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
                return oldItem == newItem // Use data class equality check
            }
        }
    }
}
