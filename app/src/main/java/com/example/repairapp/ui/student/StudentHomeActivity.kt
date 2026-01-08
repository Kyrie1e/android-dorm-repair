package com.example.repairapp.ui.student

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.repairapp.R
import com.example.repairapp.ui.order.NewOrderActivity
import com.example.repairapp.ui.order.OrderListActivity

class StudentHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)

        val myOrdersButton = findViewById<Button>(R.id.button_my_orders)
        val newOrderButton = findViewById<Button>(R.id.button_new_order)

        myOrdersButton.setOnClickListener {
            val intent = Intent(this, OrderListActivity::class.java)
            intent.putExtra(OrderListActivity.EXTRA_MODE, OrderListActivity.MODE_MY)
            startActivity(intent)
        }

        newOrderButton.setOnClickListener {
            startActivity(Intent(this, NewOrderActivity::class.java))
        }
    }
}
