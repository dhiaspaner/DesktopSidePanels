package org.company.app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


@Composable
fun ItemsGrid (
    modifier: Modifier= Modifier,
    text: String,
    itemsNumber: Int = 5,
    size: Size
) {
    val density = LocalDensity.current
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .padding(10.dp)
            .size(with(density) {size.toDpSize()})
    ) {
        items(itemsNumber) { index: Int ->
            Item(
                modifier = Modifier,
                text = "$text $index"
            )
        }


    }
}