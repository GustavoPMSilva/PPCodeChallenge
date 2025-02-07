package com.example.paypalcodechallenge.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LabelCheckbox(
    label: String,
    onCheckedChange: () -> Unit,
    isChecked: Boolean
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onCheckedChange)
            .padding(4.dp)
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = null
        )
        Spacer(
            modifier = Modifier.size(6.dp)
        )
        Text(
            text = label
        )
    }
}