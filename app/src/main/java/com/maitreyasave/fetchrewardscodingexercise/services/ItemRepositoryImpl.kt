package com.maitreyasave.fetchrewardscodingexercise.services

import com.maitreyasave.fetchrewardscodingexercise.data.Item

class ItemRepositoryImpl(private val apiService: ApiService) : ItemRepository {
    override suspend fun getItems(): List<Item> {
        return apiService.getItems()
    }
}
