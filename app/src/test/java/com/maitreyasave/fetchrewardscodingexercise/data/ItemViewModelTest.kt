package com.maitreyasave.fetchrewardscodingexercise.data

import com.maitreyasave.fetchrewardscodingexercise.services.ItemRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ItemViewModelTest {

    private lateinit var viewModel: ItemViewModel
    private val repository: ItemRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Set test dispatcher
        viewModel = ItemViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset to default dispatcher
    }

    @Test
    fun `fetchItems groups and sorts items correctly`() = runTest {
        val mockItems = listOf(
            Item(2, 1, "Banana"),
            Item(1, 1, "Apple"),
            Item(3, 2, "Cherry"),
            Item(4, 1, "   ") // Blank name (should be filtered out)
        )
        coEvery { repository.getItems() } returns mockItems

        viewModel.fetchItems()

        advanceUntilIdle() // Ensure all coroutines complete

        val expected = mapOf(
            1 to listOf(Item(1, 1, "Apple"), Item(2, 1, "Banana")),
            2 to listOf(Item(3, 2, "Cherry"))
        )
        assertEquals(expected, viewModel.groupedItems)
    }
}
