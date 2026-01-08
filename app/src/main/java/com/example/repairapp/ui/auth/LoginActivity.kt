package com.example.repairapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.repairapp.R
import com.example.repairapp.ui.launch.LauncherActivity
import com.example.repairapp.ui.student.StudentHomeActivity
import com.example.repairapp.ui.worker.WorkerHomeActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameInput = findViewById<EditText>(R.id.edit_username)
        val passwordInput = findViewById<EditText>(R.id.edit_password)
        val loginButton = findViewById<Button>(R.id.button_login)
        val registerButton = findViewById<Button>(R.id.button_register)

        loginButton.setOnClickListener {
            val prefs = getSharedPreferences(LauncherActivity.PREFS_NAME, MODE_PRIVATE)
            val role = prefs.getString(LauncherActivity.KEY_ROLE, LauncherActivity.ROLE_STUDENT)
                ?: LauncherActivity.ROLE_STUDENT
            prefs.edit()
                .putBoolean(LauncherActivity.KEY_LOGGED_IN, true)
                .apply()

            if (role == LauncherActivity.ROLE_WORKER) {
                startActivity(Intent(this, WorkerHomeActivity::class.java))
            } else {
                startActivity(Intent(this, StudentHomeActivity::class.java))
            }
            finish()
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }
}
