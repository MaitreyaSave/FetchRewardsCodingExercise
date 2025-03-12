package com.maitreyasave.fetchrewardscodingexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.maitreyasave.fetchrewardscodingexercise.data.Item
import com.maitreyasave.fetchrewardscodingexercise.data.ItemViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }
}


// Composable UI
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(viewModel: ItemViewModel = ViewModelProvider.NewInstanceFactory().create(ItemViewModel::class.java)) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Fetch Data") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            ItemList(groupedItems = viewModel.groupedItems)
        }
    }
}

@Composable
fun ItemList(groupedItems: Map<Int, List<Item>>) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        groupedItems.keys.sorted().forEach { listId ->
            item {
                Text(text = "List ID: $listId", style = MaterialTheme.typography.headlineSmall)
            }
            items(groupedItems[listId] ?: emptyList()) { item ->
                Text(text = "• ${item.name}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
