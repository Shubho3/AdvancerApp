package com.tensor.example.ui.home.friendsList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tensor.example.data.fireatstore.model.User
import com.tensor.example.databinding.ItemFriendBinding

class FriendsAdapter(
    private val messageList: ArrayList<User>, private val listener: AdapterClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class RHolder(val binding: ItemFriendBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RHolder(binding)
    }
    override fun getItemCount(): Int {
        return messageList.size
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageList[position]
        if (holder is RHolder) {
            holder.binding.data =message
        holder.itemView.setOnClickListener { listener.onItemClick(message,position) }
        }

    }
    public interface AdapterClickListener {
        fun onItemClick(model: User, int: Int)
    }
}