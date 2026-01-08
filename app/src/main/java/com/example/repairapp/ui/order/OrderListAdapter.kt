package com.example.repairapp.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.repairapp.R

class OrderListAdapter(
    private val items: List<OrderItem>,
    private val onItemClick: (OrderItem) -> Unit
) : RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class OrderViewHolder(
        itemView: View,
        private val onItemClick: (OrderItem) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.text_order_title)
        private val statusView: TextView = itemView.findViewById(R.id.text_order_status)
        private val handlerView: TextView = itemView.findViewById(R.id.text_order_handler)

        fun bind(item: OrderItem) {
            titleView.text = "${item.type} - ${item.location}"
            statusView.text = "状态：${item.status}"
            handlerView.text = "接单师傅：${item.handlerName}"
            itemView.setOnClickListener { onItemClick(item) }
        }
    }
}


data class OrderItem(
    val id: Long,
    val type: String,
    val location: String,
    val status: String,
    val handlerName: String
)
