package org.company.app.panel

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object PanelConstraints {

    val zero = Constraints(
        minWidth = 0,
        maxWidth = 0,
        maxHeight = 0,
        minHeight = 0
    )

    val topPanelConstraints = Constraints(
        minHeight = 50,
        maxHeight = 200,
    )

    val bottomPanelConstraints = Constraints(
        minHeight = 50,
        maxHeight = 200,
    )

    val leftPanelConstraints = Constraints(
        minWidth = 50,
        maxWidth = 200,
    )

    val rightPanelConstraints = Constraints(
        minWidth = 50,
        maxWidth = 200,
    )

    fun pickConstraints(panelSide: PanelSide): Constraints {
        return when(panelSide) {
            is PanelSide.Top -> topPanelConstraints
            is PanelSide.Bottom -> bottomPanelConstraints
            is PanelSide.Left -> leftPanelConstraints
            is PanelSide.Right -> rightPanelConstraints
            is PanelSide.Free -> Constraints()
        }
    }
}

data class ConstraintSize(
    val minSize: Dp,
    val maxSize: Dp
)

