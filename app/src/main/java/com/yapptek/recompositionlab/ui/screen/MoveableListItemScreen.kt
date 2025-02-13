package com.yapptek.recompositionlab.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun MoveableListItemScreen(items: List<ListItem>, modifier: Modifier) {
    val itemsState = remember {
        mutableStateOf(items)
    }
    Column(modifier.padding(horizontal = 16.dp)) {
        Text("Moveable list item screen", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        ListComponent(itemsState)
    }
}

@Composable
private fun ListComponent(listItemsState: State<List<ListItem>>, modifier: Modifier = Modifier) {
    val listItemComponent = remember {
        movableContentOf<ListItem> { item ->
            MoveableListItemComponent(item)
        }
    }

    val listItems by listItemsState
    LazyColumn(modifier = modifier) {
        items(listItems, key = { it.key }) { item ->
            listItemComponent(item)
        }
    }
}

@Composable
private fun MoveableListItemComponent(listItem: ListItem) {
    var isChecked by rememberSaveable { mutableStateOf(false) }
    Row(
        Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Text(listItem.index.toString(), style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.width(16.dp))
        Text(listItem.title, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.weight(1f))
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
            }
        )
    }
}
