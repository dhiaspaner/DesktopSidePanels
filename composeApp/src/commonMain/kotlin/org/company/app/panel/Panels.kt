package org.company.app.panel

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

class ToolPanel : Panel() {

    override var moveOffset by mutableStateOf(Offset.Zero)
    override var isFloating: Boolean by mutableStateOf(false)
    override var originOffset: IntOffset = IntOffset.Zero
    override var size: Size by mutableStateOf(Size.Zero)
    override var isTouched: Boolean by mutableStateOf(false)
    override var isShadowed: Boolean by mutableStateOf(false)
    override var panelSide: PanelSide by mutableStateOf(PanelSide.Top)
    override var position by mutableIntStateOf(0)
    override var constraints by mutableStateOf(PanelConstraints.topPanelConstraints)


    @Composable
    override fun content(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Blue)
        ) {
            PanelBounder(
                panel = this@ToolPanel,
                modifier = modifier
            )
            if (size.width > size.height) {
                Row {
                    (1..5).forEach {
                        Item(
                            modifier = Modifier,
                            text = "ToolPanel $it"
                        )
                    }
                }
            } else Column {
                (1..5).forEach {
                    Item(
                        modifier = Modifier,
                        text = "ToolPanel $it"
                    )
                }
            }
        }
    }
}

@Composable
fun BoxScope.PanelBounder(
    panel: Panel,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .background(color = Color.Black)
            .width(2.dp)
            .fillMaxHeight()
            .align(
                Alignment.TopStart
            )
            .pointerInput(panel.panelSide) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        panel.size = panel.size.copy(
                            width = panel.size.width - dragAmount.x
                        )
                        panel.moveOffset = panel.moveOffset.copy(
                            x = panel.moveOffset.x + dragAmount.x
                        )
                    }

                )
            }
    )
    Box(
        modifier = modifier
            .background(color = Color.Black)
            .width(2.dp)
            .fillMaxHeight()
            .align(
                Alignment.TopEnd
            )
            .pointerInput(panel.panelSide) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        panel.size = panel.size.copy(
                            width = panel.size.width + dragAmount.x
                        )
                    }
                )
            }
    )
    Box(
        modifier = modifier
            .background(color = Color.Black)
            .height(2.dp)
            .fillMaxWidth()
            .align(
                Alignment.TopStart
            )
            .pointerInput(panel.panelSide) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        panel.size = panel.size.copy(
                            height = panel.size.height - dragAmount.y
                        )


                        panel.moveOffset = panel.moveOffset.copy(
                            y = panel.moveOffset.y + dragAmount.y
                        )
                    }
                )
            }

    )
    Box(
        modifier = modifier
            .background(color = Color.Black)
            .height(2.dp)
            .fillMaxWidth()
            .align(
                Alignment.BottomEnd
            )
            .pointerInput(panel.panelSide) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        if (panel.size.height - dragAmount.y > 50) {
                            panel.size = panel.size.copy(
                                height = panel.size.height + dragAmount.y

                            )
                        }
                    }
                )
            }
    )
}


class PaginationPanel : Panel() {

    override var moveOffset by mutableStateOf(Offset.Zero)
    override var originOffset: IntOffset = IntOffset.Zero
    override var isFloating: Boolean by mutableStateOf(false)
    override var size: Size by mutableStateOf(Size.Zero)
    override var isTouched: Boolean by mutableStateOf(false)
    override var isShadowed: Boolean by mutableStateOf(false)
    override var panelSide: PanelSide by mutableStateOf(PanelSide.Left)
    override var position by mutableIntStateOf(0)
    override var constraints by mutableStateOf(PanelConstraints.leftPanelConstraints)


    @Composable
    override fun content(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Gray)
        ) {
            if (!isShadowed) {
                PanelBounder(
                    panel = this@PaginationPanel,
                    modifier = modifier
                )

                if (size.width > size.height) {
                    Row {
                        (1..5).forEach {
                            Item(
                                modifier = Modifier,
                                text = "PaginationPanel $it"
                            )
                        }
                    }
                } else Column {
                    (1..5).forEach {
                        Item(
                            modifier = Modifier,
                            text = "PaginationPanel $it"
                        )
                    }
                }
            }
        }
    }

}

class ConfigPanel : Panel() {

    override var moveOffset by mutableStateOf(Offset.Zero)
    override var isFloating: Boolean by mutableStateOf(false)
    override var originOffset: IntOffset = IntOffset.Zero
    override var size: Size by mutableStateOf(Size.Zero)
    override var isTouched: Boolean by mutableStateOf(false)
    override var isShadowed: Boolean by mutableStateOf(false)
    override var panelSide: PanelSide by mutableStateOf(PanelSide.Right)
    override var position by mutableIntStateOf(0)
    override var constraints by mutableStateOf(PanelConstraints.rightPanelConstraints)


    @Composable
    override fun content(modifier: Modifier) {
        Box(
            modifier = modifier
                .background(color = Color.Yellow)
        ) {
            PanelBounder(
                panel = this@ConfigPanel,
                modifier = modifier
            )

            if (size.width > size.height) {
                Row {
                    (1..5).forEach {
                        Item(
                            modifier = Modifier,
                            text = "ConfigPanel $it"
                        )
                    }
                }
            } else Column {
                (1..5).forEach {
                    Item(
                        modifier = Modifier,
                        text = "ConfigPanel $it"
                    )
                }
            }
        }
    }
}

class FreePanel : Panel() {

    override var moveOffset by mutableStateOf(Offset.Zero)
    override var isFloating: Boolean by mutableStateOf(false)
    override var originOffset: IntOffset = IntOffset.Zero
    override var size: Size by mutableStateOf(Size.Zero)
    override var isTouched: Boolean by mutableStateOf(false)
    override var isShadowed: Boolean by mutableStateOf(false)
    override var panelSide: PanelSide by mutableStateOf(PanelSide.Bottom)
    override var position by mutableIntStateOf(0)
    override var constraints by mutableStateOf(PanelConstraints.bottomPanelConstraints)


    @Composable
    override fun content(modifier: Modifier) {
        Box(
            modifier = modifier
                .background(color = Color.Green)
        ) {
            PanelBounder(
                panel = this@FreePanel,
                modifier = modifier
            )
        }
    }
}

class ShadowedPanel : Panel() {

    override var moveOffset by mutableStateOf(Offset.Zero)
    override var isFloating: Boolean by mutableStateOf(false)
    override var originOffset: IntOffset = IntOffset.Zero
    override var size: Size by mutableStateOf(Size.Zero)
    override var isTouched: Boolean by mutableStateOf(false)
    override var isShadowed: Boolean by mutableStateOf(false)
    override var panelSide: PanelSide by mutableStateOf(PanelSide.Free)
    override var position by mutableIntStateOf(0)
    override var constraints by mutableStateOf(PanelConstraints.zero)


    @Composable
    override fun content(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Gray.copy(alpha = 0.4f))
        ) {

        }
    }

    fun adjustWith(panel: Panel) {
        size = panel.size
    }

    fun reset() {
        panelSide = PanelSide.Free
        constraints = PanelConstraints.zero
        moveOffset = Offset.Zero
        size = Size.Zero
    }
}