package org.company.app.item

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface Item {

    val id: String


    @Composable
    fun content(modifier: Modifier)
}