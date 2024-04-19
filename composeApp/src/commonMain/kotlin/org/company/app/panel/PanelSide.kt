package org.company.app.panel

sealed class PanelSide {
    data object Top : PanelSide()
    data object Bottom : PanelSide()
    data object Left : PanelSide()
    data object Right : PanelSide()
    data object Free : PanelSide()
}