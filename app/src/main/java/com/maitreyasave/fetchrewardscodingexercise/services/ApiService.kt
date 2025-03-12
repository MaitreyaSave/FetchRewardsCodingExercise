package com.maitreyasave.fetchrewardscodingexercise.services

import com.maitreyasave.fetchrewardscodingexercise.data.Item
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}
