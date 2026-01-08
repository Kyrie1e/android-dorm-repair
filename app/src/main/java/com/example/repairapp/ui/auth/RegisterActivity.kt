package com.example.repairapp.ui.auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.repairapp.R
import com.example.repairapp.ui.launch.LauncherActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usernameInput = findViewById<EditText>(R.id.edit_username)
        val passwordInput = findViewById<EditText>(R.id.edit_password)
        val roleGroup = findViewById<RadioGroup>(R.id.role_group)
        val registerButton = findViewById<Button>(R.id.button_register)

        registerButton.setOnClickListener {
            val selectedRole = when (roleGroup.checkedRadioButtonId) {
                R.id.role_worker -> LauncherActivity.ROLE_WORKER
                else -> LauncherActivity.ROLE_STUDENT
            }
            val prefs = getSharedPreferences(LauncherActivity.PREFS_NAME, MODE_PRIVATE)
            prefs.edit()
                .putString(LauncherActivity.KEY_ROLE, selectedRole)
                .apply()
            finish()
        }

    }
}
