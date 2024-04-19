package org.company.app.panel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


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


@Composable
fun Content() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(IntrinsicSize.Max)
        ) {
            (1..5).forEach {
                Item(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Item $it"
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(intrinsicSize = IntrinsicSize.Max)
        ) {
            (1..5).forEach {
                Item(
                    modifier = Modifier.weight(1f),
                    text = "Item $it"
                )
            }
        }
    }
}


@Composable
fun Item(
    modifier: Modifier = Modifier,
    text: String
) {

    Box(
        modifier = modifier
            .background(
                color = Color.Cyan,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(
                shape = RoundedCornerShape(10.dp)
            )
            .height(40.dp)

    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 10.dp),
            text = text
        )
    }
}

