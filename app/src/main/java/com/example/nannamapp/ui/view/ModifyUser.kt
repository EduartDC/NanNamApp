package com.example.nannamapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.example.nannamapp.R
import com.example.nannamapp.databinding.ActivityModifyUserBinding

class ModifyUser : AppCompatActivity() {
    private lateinit var binding: ActivityModifyUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showLinearLayout_password(view: View) {
        binding.linearLayoutPassword.visibility = View.VISIBLE
        binding.modifyPassword.visibility = View.GONE
    }

    fun hideLinearLayout_password(view: View) {
        binding.linearLayoutPassword.visibility = View.GONE
        binding.modifyPassword.visibility = View.VISIBLE
    }
}