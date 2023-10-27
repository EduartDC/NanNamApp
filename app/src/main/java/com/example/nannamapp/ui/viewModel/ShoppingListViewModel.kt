package com.example.nannamapp.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nannamapp.data.model.ShoppingItem
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class ShoppingListViewModel(private val context: Context) : ViewModel() {

    private val _shoppingList = MutableLiveData<MutableList<ShoppingItem>>()
    val shoppingList: LiveData<MutableList<ShoppingItem>> get() = _shoppingList

    init {
        _shoppingList.value = loadShoppingList()
    }

    fun loadShoppingList(): MutableList<ShoppingItem> {

        val sharedPreferences = context.getSharedPreferences("shopping_prefs", Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("shopping_list", "[]")

        return try {
            val typeToken = object : TypeToken<MutableList<ShoppingItem>>() {}.type
            Gson().fromJson(jsonString, typeToken)
        } catch (e: JsonSyntaxException) {
            mutableListOf()
        }
    }

    fun saveShoppingList() {
        val sharedPreferences = context.getSharedPreferences("shopping_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val jsonString = Gson().toJson(_shoppingList.value)
        editor.putString("shopping_list", jsonString)

        editor.apply()
    }

    fun clearSelectedItems() {
        val itemsToKeep = _shoppingList.value?.filter { !it.isSelected } ?: emptyList()

        _shoppingList.value?.clear()
        _shoppingList.value?.addAll(itemsToKeep)

        saveShoppingList()
    }
}