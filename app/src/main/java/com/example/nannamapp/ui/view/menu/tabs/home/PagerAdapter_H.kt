package com.example.nannamapp.ui.view.menu.tabs.home

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter_H (private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Recipes()
            1 -> PrepareRecipe()
            else -> Recipes()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Recetario"
            1 -> "Preparar Receta"
            else -> ""
        }
    }

    override fun getCount(): Int {
        return 2
    }
}