package com.maitreyasave.fetchrewardscodingexercise.services

import com.maitreyasave.fetchrewardscodingexercise.data.Item
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ItemRepositoryTest {

    private lateinit var repository: ItemRepository
    private val apiService: ApiService = mockk()

    @Before
    fun setup() {
        // Initialize repository with mocked ApiService
        repository = ItemRepositoryImpl(apiService)
    }

    @Test
    fun `getItems returns filtered items without sorting or grouping`() = runTest {
        // Given: Mock response from ApiService
        val mockItems = listOf(
            Item(2, 1, "Banana"),
            Item(1, 1, "Apple"),
            Item(3, 2, "Cherry")
        )
        coEvery { apiService.getItems() } returns mockItems

        // When: Call getItems() from repository
        val result = repository.getItems()

        // Then: Verify the result is correctly filtered, without sorting or grouping
        val expected = listOf(
            Item(2, 1, "Banana"),
            Item(1, 1, "Apple"),
            Item(3, 2, "Cherry")
        )

        assertEquals(expected, result)
    }


    @Test
    fun `getItems handles empty list from API`() = runTest {
        // Given: Empty list from ApiService
        coEvery { apiService.getItems() } returns emptyList()

        // When: Call getItems() from repository
        val result = repository.getItems()

        // Then: Verify the result is an empty map
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getItems handles exception from API`() = runTest {
        // Given: Exception thrown by ApiService
        coEvery { apiService.getItems() } throws Exception("Network error")

        // When: Call getItems() from repository
        try {
            repository.getItems()
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            // Then: Verify that exception is thrown
            assertEquals("Network error", e.message)
        }
    }
}
