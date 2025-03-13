package com.maitreyasave.fetchrewardscodingexercise.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maitreyasave.fetchrewardscodingexercise.services.ItemRepository

class ItemViewModelFactory(private val repository: ItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

