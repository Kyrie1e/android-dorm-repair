package com.example.repairapp.ui.order

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.repairapp.R

class OrderDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val orderId = intent.getLongExtra(OrderListActivity.EXTRA_ORDER_ID, -1L)
        val titleView = findViewById<TextView>(R.id.text_title)
        titleView.text = "工单详情 #$orderId"
    }
}
