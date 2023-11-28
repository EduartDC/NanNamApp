package com.example.nannamapp.ui.view.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.nannamapp.R
import com.example.nannamapp.databinding.ActivityStartMenuBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class StartMenu : AppCompatActivity() {

    private lateinit var binding: ActivityStartMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false
        setFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> setFragment(HomeFragment())
                R.id.search -> setFragment(SearchFragment())
                R.id.list -> setFragment(ListFragment())
                R.id.userAccount -> setFragment(UserAccountFragment())
                else -> { }
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
    }

}