package org.company.app.layout

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import org.company.app.panel.Panel
import org.company.app.panel.PanelSide
import org.company.app.toIntOffset
import org.company.app.toSize
import kotlin.math.roundToInt


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
