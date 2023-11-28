package com.example.nannamapp.ui.view.menu

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.nannamapp.R
import com.example.nannamapp.ui.view.IngredientListActivity
import com.example.nannamapp.ui.view.MainActivity
import com.example.nannamapp.ui.view.SelectPreferencesActivity
import com.google.android.material.tabs.TabLayout


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UserAccountFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_account, container, false)

        val dietaryPreferencesCardView: CardView = view.findViewById(R.id.DietaryPreferences)
        val logOutCardView: CardView = view.findViewById(R.id.LogOut)

        dietaryPreferencesCardView.setOnClickListener {
            val intent = Intent(requireContext(), SelectPreferencesActivity::class.java)
            startActivity(intent)
        }


        logOutCardView.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        return view
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Cerrar Sesión")
        builder.setMessage("¿Estás de acuerdo en cerrar sesión?")
        builder.setPositiveButton("Sí") { _: DialogInterface, _: Int ->
            logoutAndRestartApp()
        }
        builder.setNegativeButton("No") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun logoutAndRestartApp() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        requireActivity().finish()
    }
    companion object {
                @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserAccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}