package com.example.repairapp.ui.order

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repairapp.R

class OrderListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        val mode = intent.getStringExtra(EXTRA_MODE) ?: MODE_MY
        val titleView = findViewById<TextView>(R.id.text_title)
        titleView.text = if (mode == MODE_ALL) "全部工单" else "我的工单"

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_orders)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val data = buildFakeData(mode)
        val adapter = OrderListAdapter(data) { item ->
            val intent = Intent(this, OrderDetailActivity::class.java)
            intent.putExtra(EXTRA_ORDER_ID, item.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    private fun buildFakeData(mode: String): List<OrderItem> {
        return if (mode == MODE_ALL) {
            listOf(
                OrderItem(1, "水电维修", "1号楼 201", "处理中", "师傅A"),
                OrderItem(2, "门锁报修", "3号楼 502", "待接单", "未接单"),
                OrderItem(3, "空调故障", "2号楼 408", "已完成", "师傅B")
            )
        } else {
            listOf(
                OrderItem(10, "灯泡更换", "5号楼 103", "待接单", "未接单"),
                OrderItem(11, "水管漏水", "5号楼 105", "处理中", "师傅C")
            )
        }
    }

    companion object {
        const val EXTRA_MODE = "mode"
        const val EXTRA_ORDER_ID = "orderId"
        const val MODE_MY = "MY"
        const val MODE_ALL = "ALL"
    }
}
