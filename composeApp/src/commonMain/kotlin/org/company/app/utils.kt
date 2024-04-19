package org.company.app

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.IntOffset
import org.company.app.panel.Panel


@OptIn(ExperimentalFoundationApi::class)
fun Modifier.draggablePanel(
    panel: Panel,
    onDragStart: (Panel) -> Unit,
    onDrag: (Panel) -> Unit,
    onDragEnd: (Panel) -> Unit = {}
): Modifier = then(
    Modifier
        .onDrag(
            onDragStart = {
                onDragStart(panel)
            },
            onDrag = { change ->
                panel.moveOffset += change
                onDrag(panel)
            },
            onDragEnd = {
                onDragEnd(panel)
            }
        )
)

fun Offset.toIntOffset(): IntOffset {
    return IntOffset(this.x.toInt(), this.y.toInt())
}

fun IntOffset.isZero(): Boolean {
    return this.x == 0 && this.y == 0
}

fun Placeable.toSize() = Size(width = width.toFloat(), height = height.toFloat())

