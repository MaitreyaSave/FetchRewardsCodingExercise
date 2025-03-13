package com.maitreyasave.fetchrewardscodingexercise.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maitreyasave.fetchrewardscodingexercise.services.ItemRepository
import com.maitreyasave.fetchrewardscodingexercise.services.ItemRepositoryImpl
import com.maitreyasave.fetchrewardscodingexercise.services.RetrofitClient
import kotlinx.coroutines.launch

class ItemViewModel(
    private val repository: ItemRepository = ItemRepositoryImpl(RetrofitClient.api)
) : ViewModel() {

    var groupedItems by mutableStateOf<Map<Int, List<Item>>>(emptyMap())
        private set

    init {
        fetchItems()
    }

    fun fetchItems() {
        viewModelScope.launch {
            try {
                val items = repository.getItems()
                    .filter { !it.name.isNullOrBlank() }
                    .sortedWith(compareBy({ it.listId }, { it.name }))
                    .groupBy { it.listId }

                groupedItems = items
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}