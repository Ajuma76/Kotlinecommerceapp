package com.example.shoplyte.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoplyte.R
import com.example.shoplyte.databinding.ActivityForgotPasswordBinding

class ForgotPassword : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarForgotPasswordActivity)

        val  actionbar = supportActionBar
        if (actionbar != null)
        {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24)
        }
        binding.toolbarForgotPasswordActivity.setNavigationOnClickListener { onBackPressed() }
    }
}