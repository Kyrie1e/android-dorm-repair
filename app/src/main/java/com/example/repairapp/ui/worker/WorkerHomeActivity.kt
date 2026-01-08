package com.example.repairapp.ui.worker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.repairapp.R
import com.example.repairapp.ui.order.OrderListActivity

class WorkerHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_home)

        val allOrdersButton = findViewById<Button>(R.id.button_all_orders)
        allOrdersButton.setOnClickListener {
            val intent = Intent(this, OrderListActivity::class.java)
            intent.putExtra(OrderListActivity.EXTRA_MODE, OrderListActivity.MODE_ALL)
            startActivity(intent)
        }
    }
}
