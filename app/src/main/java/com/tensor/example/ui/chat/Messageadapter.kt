package com.tensor.example.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tensor.example.databinding.ReceiveBinding
import com.tensor.example.databinding.SentBinding
data class Message(val message: String, val userId: String)

class MessageAdapter(private val messageList :ArrayList<Message>,
                     private val currentUserId: String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SentMessageHolder(val binding: SentBinding) : RecyclerView.ViewHolder(binding.root)
    class ReceivedMessageHolder(val binding: ReceiveBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        val message = messageList[position]
        return if (message.userId == currentUserId) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            val binding = SentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            SentMessageHolder(binding)
        } else {
            val binding = ReceiveBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            ReceivedMessageHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageList[position]
        if (holder is SentMessageHolder) {
            holder.binding.sentmessagetext.text = message.message
           /* holder.binding.user.load("") {
                crossfade(true)
                placeholder(R.drawable.range_car)
                transformations(CircleCropTransformation())
            }*/
        } else if (holder is ReceivedMessageHolder) {
            holder.binding.receivemessagetext.text = message.message
        // receivedmessagetext TextView'inizin adı
           /* holder.binding.user.load("") {
                crossfade(true)
                placeholder(R.drawable.range_car)
                transformations(CircleCropTransformation())
            }*/
        }
    }

    companion object {
        const val VIEW_TYPE_SENT = 1
        const val VIEW_TYPE_RECEIVED = 2
    }
}