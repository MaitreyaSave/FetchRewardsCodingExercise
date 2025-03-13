package com.maitreyasave.fetchrewardscodingexercise.services

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class ApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start() // Ensure MockWebServer starts before using it

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // This now works correctly
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getItems returns expected list`() = runTest {  // Use runTest instead of runBlocking
        val mockResponse = """
            [
                {"id": 1, "listId": 1, "name": "Item 1"},
                {"id": 2, "listId": 1, "name": "Item 2"}
            ]
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockResponse).setResponseCode(200))

        val items = apiService.getItems()

        assertEquals(2, items.size)
        assertEquals(1, items[0].id)
        assertEquals(1, items[0].listId)
        assertEquals("Item 1", items[0].name)
    }

    @Test
    fun `getItems handles empty response`() = runTest { // Use runTest here too
        mockWebServer.enqueue(MockResponse().setBody("[]").setResponseCode(200))

        val items = apiService.getItems()

        assertEquals(0, items.size)
    }
}
