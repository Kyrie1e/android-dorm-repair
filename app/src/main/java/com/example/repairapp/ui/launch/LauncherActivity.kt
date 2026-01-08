package com.example.repairapp.ui.launch

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.repairapp.ui.auth.LoginActivity
import com.example.repairapp.ui.student.StudentHomeActivity
import com.example.repairapp.ui.worker.WorkerHomeActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val isLoggedIn = prefs.getBoolean(KEY_LOGGED_IN, false)
        if (isLoggedIn) {
            when (prefs.getString(KEY_ROLE, ROLE_STUDENT)) {
                ROLE_WORKER -> startActivity(Intent(this, WorkerHomeActivity::class.java))
                else -> startActivity(Intent(this, StudentHomeActivity::class.java))
            }
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }

    companion object {
        const val PREFS_NAME = "repair_session"
        const val KEY_LOGGED_IN = "logged_in"
        const val KEY_ROLE = "role"
        const val ROLE_STUDENT = "student"
        const val ROLE_WORKER = "worker"
    }
}
