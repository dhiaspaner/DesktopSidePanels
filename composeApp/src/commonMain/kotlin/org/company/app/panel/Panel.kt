package org.company.app.panel

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.toOffset
import java.util.*

abstract class Panel {
    val id: String =  UUID.randomUUID().toString()
    abstract var moveOffset: Offset
    abstract var originOffset: IntOffset
    abstract var isFloating: Boolean
    abstract var size: Size
    abstract var isTouched: Boolean
    abstract var isShadowed: Boolean
    abstract var panelSide: PanelSide
    abstract var position: Int
    abstract var constraints: Constraints

    val offset
        get() = originOffset.toOffset() + moveOffset

    val rect: Rect
        get() = Rect(
            offset = originOffset.toOffset(),
            size = size
        )

    @Composable
    abstract fun content(modifier: Modifier)



    override fun equals(other: Any?): Boolean {
        return if (other is Panel) other.id == id
        else false
    }


}