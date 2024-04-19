package org.company.app.panel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

class PanelManager {

    val toolsPanel = ToolPanel()
    val paginationPanel = PaginationPanel()
    val configPanel = ConfigPanel()
    val freePanel = FreePanel()
    val shadowedPanel = ShadowedPanel()

    val panels = listOf(
        toolsPanel,
        freePanel,
        paginationPanel,
        configPanel,
        shadowedPanel
    )


    var dragEndAApplied by mutableIntStateOf(0)

    var maxHeight by mutableIntStateOf(0)
    var maxWidth by mutableIntStateOf(0)

    var topSplitter = 0
    var bottomSplitter = 0
    var leftSplitter = 0
    var rightSplitter = 0


    fun onDragStart(panel: Panel) {
        panel.panelSide = PanelSide.Free
        if (panel.size.width == maxWidth.toFloat() || panel.size.height == maxHeight.toFloat()) {
            panel.size = Size(
                width = maxWidth / 3f,
                height = maxHeight / 3f
            )
        }

    }

    fun onDrag(panel: Panel) {
        panel.detectShadowedPanel()
    }


    fun onDragEnd(panel: Panel) {
        if (shadowedPanel.panelSide is PanelSide.Free) return
        panel.panelSide = shadowedPanel.panelSide
        panel.moveOffset = Offset.Zero
        panel.constraints = shadowedPanel.constraints
        shadowedPanel.reset()
    }


    private fun Panel.detectShadowedPanel() {
        val panelTopSideSizeRatio = if (offset.y > topSplitter) 0f
        else if (offset.y + size.height < topSplitter) 1f
        else (topSplitter - offset.y) / topSplitter

        val panelBottomSideSizeRatio = if (offset.y + size.height < bottomSplitter) 0f
        else if (offset.y > bottomSplitter) 1f
        else 1f - (size.height - bottomSplitter) / size.height

        val panelLeftSideSizeRatio = if (offset.x > leftSplitter) 0f
        else if (offset.x + size.width < leftSplitter) 1f
        else (leftSplitter - offset.x) / leftSplitter

        val panelRightSideSizeRatio = if (offset.x + size.width < rightSplitter) 0f
        else if (offset.x  > rightSplitter) 1f
        else 1f -  (size.width - rightSplitter) / size.width

        val maxRatio =
            maxOf(panelTopSideSizeRatio, panelBottomSideSizeRatio, panelLeftSideSizeRatio, panelRightSideSizeRatio)


        when (maxRatio) {
            0f -> shadowedPanel.reset()
            panelTopSideSizeRatio -> {
                shadowedPanel.panelSide = PanelSide.Top
                shadowedPanel.constraints = PanelConstraints.topPanelConstraints
            }

            panelBottomSideSizeRatio -> {
                shadowedPanel.panelSide = PanelSide.Bottom
                shadowedPanel.constraints = PanelConstraints.bottomPanelConstraints
            }

            panelLeftSideSizeRatio -> {
                shadowedPanel.panelSide = PanelSide.Left
                shadowedPanel.constraints = PanelConstraints.leftPanelConstraints
            }

            panelRightSideSizeRatio -> {
                shadowedPanel.panelSide = PanelSide.Right
                shadowedPanel.constraints = PanelConstraints.rightPanelConstraints
            }
        }
    }
}


fun IntSize.toSize() = Size(width.toFloat(), height.toFloat())