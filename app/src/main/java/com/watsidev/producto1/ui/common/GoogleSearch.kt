package com.watsidev.producto1.ui.common

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.watsidev.producto1.R

@Composable
fun GoogleSearch(
    value: String,
    valueChanged: (String) -> Unit,
    context: Context,
    onQuerySearch: (String, Context) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { valueChanged(it) },
        leadingIcon = {
            Image(
                painterResource(R.drawable.google_color),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
        },
        trailingIcon = {
            IconButton(
                onClick = { onQuerySearch(value, context) }
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                )
            }
        },
        placeholder = {
            Text(stringResource(R.string.google_search))
        },
        shape = RoundedCornerShape(100),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
        ),
        maxLines = 1,
        singleLine = true,
        keyboardActions = KeyboardActions(
            onSearch = { onQuerySearch(value, context) }
        ),
        keyboardOptions = KeyboardOptions(
            autoCorrect = true,
            imeAction = ImeAction.Search
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}