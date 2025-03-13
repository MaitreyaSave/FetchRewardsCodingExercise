package com.maitreyasave.fetchrewardscodingexercise.services

import com.maitreyasave.fetchrewardscodingexercise.data.Item

interface ItemRepository {
    suspend fun getItems(): List<Item>
}
