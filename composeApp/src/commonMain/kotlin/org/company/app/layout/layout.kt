import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.company.app.panel.Panel
import org.company.app.panel.PanelManager
import org.company.app.panel.PanelSide
import org.company.app.panel.draggablePanel
import kotlin.math.roundToInt


@Composable
fun FourPanelsLayout(
    panelManager: PanelManager
) {
    Layout(
        content = {
            Box(
                modifier = Modifier
                    .border(
                        color = Color.Gray,
                        width = 2.dp,
                    )
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource("kotlin backround.png"),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "",
                )


            }
            panelManager.panels.forEachIndexed { index, panelLayout ->

                panelLayout.content(
                    modifier = Modifier
                        .draggablePanel(
                            panel = panelLayout,
                            onDragStart = { panel -> panelManager.onDragStart(panel) },
                            onDrag = { panel -> panelManager.onDrag(panel) },
                            onDragEnd = { panel -> panelManager.onDragEnd(panel) },
                        )
                )

            }


        },
        modifier = Modifier
    ) { measurables, constraints ->

        val panelMeasurableList = measurables.slice(1 until panelManager.panels.size + 1)
        val contentMeasurable = measurables.first()

        val panelPlaceableList = panelManager.panels.mapIndexed { index, panel ->
            customMeasure(
                measurable = panelMeasurableList[index],
                panel = panel,
                constraints = constraints,
            )
        }

        val topSidePlaceableList = panelPlaceableList.filter { it.panel.panelSide is PanelSide.Top }
        val bottomSidePlaceableList = panelPlaceableList.filter { it.panel.panelSide is PanelSide.Bottom }
        val leftSidePlaceableList = panelPlaceableList.filter { it.panel.panelSide is PanelSide.Left }
        val rightSidePlaceableList = panelPlaceableList.filter { it.panel.panelSide is PanelSide.Right }
        val freeSidePanelPlaceableList = panelPlaceableList.filter { it.panel.panelSide is PanelSide.Free }

        val topPanelListHeightSum = topSidePlaceableList.sumOf { it.placeable.height }
        val bottomPanelListHeightSum = bottomSidePlaceableList.sumOf { it.placeable.height }
        val leftPanelListWidthSum = leftSidePlaceableList.sumOf { it.placeable.width }
        val rightPanelListWidthSum = rightSidePlaceableList.sumOf { it.placeable.width }

        panelManager.topSplitter = topPanelListHeightSum
        panelManager.bottomSplitter = constraints.maxHeight - bottomPanelListHeightSum
        panelManager.leftSplitter = leftPanelListWidthSum
        panelManager.rightSplitter = constraints.maxWidth - rightPanelListWidthSum


        val contentPlaceable = contentMeasurable.measure(
            constraints.copy(
                maxWidth = constraints.maxWidth -
                        rightPanelListWidthSum -
                        leftPanelListWidthSum,
                maxHeight = constraints.maxHeight -
                        topPanelListHeightSum +
                        bottomPanelListHeightSum
            )
        )
        println("bottomPanelListHeightSum $bottomPanelListHeightSum")
        panelManager.maxWidth = constraints.maxWidth
        panelManager.maxHeight = constraints.maxHeight

        layout(constraints.maxWidth, constraints.maxHeight) {
            contentPlaceable.place(
                x = leftSidePlaceableList.sumOf { it.placeable.width },
                y = topSidePlaceableList.sumOf { it.placeable.height }
            )
            placeLeftSidePanelList(leftSidePlaceableList, topPanelListHeightSum)
            placeRightSidePanelList(rightSidePlaceableList, constraints.maxWidth, topPanelListHeightSum)
            placeTopSidePanelList(topSidePlaceableList)
            placeBottomSidePanelList(bottomSidePlaceableList, constraints.maxHeight)
            placeFreeSidePanelPlaceableList(freeSidePanelPlaceableList)

        }

    }
}

data class PanelPlaceable(
    val panel: Panel,
    val placeable: Placeable
)

fun MeasureScope.customMeasure(
    panel: Panel,
    constraints: Constraints,
    measurable: Measurable
): PanelPlaceable {
    return when (panel.panelSide) {
        is PanelSide.Top -> {
            PanelPlaceable(
                panel = panel,
                placeable = measurable.measure(
                    constraints.copy(
                        maxHeight = panel.constraints.maxHeight,
                        minHeight = panel.constraints.minHeight
                    )
                )
            )
        }

        is PanelSide.Right -> {
            PanelPlaceable(
                panel = panel,
                placeable = measurable.measure(
                    constraints.copy(
                        maxWidth = panel.constraints.maxWidth,
                        minWidth = panel.constraints.minWidth
                    )
                )
            )
        }

        is PanelSide.Left -> {
            PanelPlaceable(
                panel = panel,
                placeable = measurable.measure(
                    constraints.copy(
                        maxWidth = panel.constraints.maxWidth,
                        minWidth = panel.constraints.minWidth
                    )
                )
            )
        }

        is PanelSide.Bottom -> {
            println("botto, placeable")
            PanelPlaceable(
                panel = panel,
                placeable = measurable.measure(
                    constraints.copy(
                        maxHeight = panel.constraints.maxHeight,
                        minHeight = panel.constraints.minHeight
                    )
                )
            )
        }

        is PanelSide.Free -> {
            PanelPlaceable(
                panel = panel,
                placeable = measurable.measure(
                    constraints.copy(
                        maxWidth = panel.size.width.roundToInt(),
                        maxHeight = panel.size.height.roundToInt(),
                        minWidth = panel.size.width.roundToInt(),
                        minHeight = panel.size.height.roundToInt()
                    )
                )
            )
        }
    }.apply {
        if (panel.panelSide !is PanelSide.Free) {
            panel.size = placeable.toSize()
        }
    }
}


fun Placeable.PlacementScope.placeTopSidePanelList(panelPlaceableList: List<PanelPlaceable>) {
    var offset = 0
    panelPlaceableList.forEach { panelPlaceable ->
        val originOffset = IntOffset(x = 0, y = offset)
        panelPlaceable.placeable.place(originOffset)
        panelPlaceable.panel.originOffset = originOffset
        offset += panelPlaceable.placeable.height
    }

}

fun Placeable.PlacementScope.placeBottomSidePanelList(panelPlaceableList: List<PanelPlaceable>, maxHeight: Int) {
    var offset = maxHeight
    panelPlaceableList.forEach { panelPlaceable ->
        val originOffset = IntOffset(x = 0, y = offset - panelPlaceable.placeable.height)
        panelPlaceable.placeable.place(originOffset)
        panelPlaceable.panel.originOffset = originOffset
        offset -= panelPlaceable.placeable.height
    }
}

fun Placeable.PlacementScope.placeLeftSidePanelList(panelPlaceableList: List<PanelPlaceable>, y: Int) {
    var offset = 0
    panelPlaceableList.forEach { panelPlaceable ->
        val originOffset = IntOffset(x = offset, y = y)
        panelPlaceable.placeable.place(originOffset)
        panelPlaceable.panel.originOffset = originOffset
        offset += panelPlaceable.placeable.width
    }
}

fun Placeable.PlacementScope.placeRightSidePanelList(panelPlaceableList: List<PanelPlaceable>, maxWidth: Int, y: Int) {
    var offset = maxWidth
    panelPlaceableList.forEach { panelPlaceable ->
        val originOffset = IntOffset(x = offset - panelPlaceable.placeable.width, y = y)
        panelPlaceable.placeable.place(originOffset)
        panelPlaceable.panel.originOffset = originOffset
        offset -= panelPlaceable.placeable.width
    }
}

fun Placeable.PlacementScope.placeFreeSidePanelPlaceableList(panelPlaceableList: List<PanelPlaceable>) {
    panelPlaceableList.forEach { panelPlaceable ->
        val offset = panelPlaceable.panel.offset.toIntOffset()
        panelPlaceable.placeable.place(offset)
    }
}


fun Offset.toIntOffset(): IntOffset {
    return IntOffset(this.x.toInt(), this.y.toInt())
}

fun IntOffset.isZero(): Boolean {
    return this.x == 0 && this.y == 0
}

fun Placeable.toSize() = Size(width = width.toFloat(), height = height.toFloat())