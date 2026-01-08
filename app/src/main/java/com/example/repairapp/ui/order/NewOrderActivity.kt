package com.example.repairapp.ui.order

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.repairapp.R

class NewOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_order)

        val submitButton = findViewById<Button>(R.id.button_submit)
        submitButton.setOnClickListener {
            finish()
        }
    }
}
