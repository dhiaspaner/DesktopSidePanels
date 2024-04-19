import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.company.app.panel.PanelManager
import org.company.app.panel.PanelSide
import org.company.app.draggablePanel
import org.company.app.layout.customMeasure
import org.company.app.layout.placeBottomSidePanelList
import org.company.app.layout.placeFreeSidePanelPlaceableList
import org.company.app.layout.placeLeftSidePanelList
import org.company.app.layout.placeRightSidePanelList
import org.company.app.layout.placeTopSidePanelList


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


