package com.example.nannamapp.ui.view.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.nannamapp.R
import com.example.nannamapp.databinding.ActivityStartMenuBinding
import com.example.nannamapp.ui.view.CreateRecipeActivity
import com.example.nannamapp.ui.view.SearchRecipeActivity
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

        binding.fab.setOnClickListener(){
            goToCreateRecipe()
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            val intent = Intent(this, SearchRecipeActivity::class.java)

            when(it.itemId) {
                R.id.home -> setFragment(HomeFragment())
                R.id.search -> startActivity(intent)
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

    private fun goToCreateRecipe() {
        val i = Intent(this, CreateRecipeActivity::class.java)
        startActivity(i)
    }

}