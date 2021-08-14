package com.tlgbltcn.jetrivia.ui.component

import androidx.annotation.StringRes
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tlgbltcn.jetrivia.R

@Composable
fun JokerButton(
    onClick: (() -> Unit)? = null,
    @StringRes text: Int
) {

    val mutableState = remember { mutableStateOf(true) }

    val mainButtonColor = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.surface
    )

    Button(
        onClick = {
            mutableState.value = false
            onClick?.invoke()
        },
        colors = mainButtonColor,
        enabled = mutableState.value
    ) {
        Text(text = stringResource(id = text))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJokerButton() {
    JokerButton(onClick = {}, text = R.string.skip)
}