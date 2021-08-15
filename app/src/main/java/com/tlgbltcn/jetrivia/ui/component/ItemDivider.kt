package com.tlgbltcn.jetrivia.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.tlgbltcn.jetrivia.R

@Composable
fun ItemDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 4.dp),
        color = colorResource(id = R.color.teal_200).copy(alpha = 0.2f),
        thickness = 1.dp
    )
}