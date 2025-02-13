package com.yapptek.recompositionlab.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NonRestartableListItemScreen(modifier: Modifier) {
    val items = remember { (0..1000).map { "Item $it" } }
    Column(modifier.padding(horizontal = 16.dp)) {
        Text("Non restartable list item screen", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        ListComponent(items)
    }
}

@Composable
fun ListComponent(listItems: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(count = listItems.count(), key = { "${listItems[it]}-$it" }) { index ->
            StaticListItem(index = index, title = listItems[index])
        }
    }
}

@NonRestartableComposable
@Composable
fun StaticListItem(index: Int, title: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(32.dp)) {
        Text(index.toString(), style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.width(16.dp))
        Text(title, style = MaterialTheme.typography.titleMedium)
    }
}
