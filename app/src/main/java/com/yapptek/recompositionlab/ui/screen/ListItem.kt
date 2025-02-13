package com.yapptek.recompositionlab.ui.screen;

data class ListItem(
    val index: Int,
    val title: String
) {
    val key = "$title-$index"
}