package org.company.app


import FourPanelsLayout
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.toOffset
import org.company.app.panel.PanelManager
import org.company.app.panel.PanelSide
import org.company.app.theme.AppTheme


@Composable
internal fun App() = AppTheme {
    val panelManager = remember {
        PanelManager()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FourPanelsLayout(
            panelManager = panelManager
        )

    }

}

internal expect fun openUrl(url: String?)