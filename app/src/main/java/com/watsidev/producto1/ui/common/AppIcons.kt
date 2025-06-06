package com.watsidev.producto1.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.watsidev.producto1.data.App

@Composable
fun AppIcons(
    app: App,
    onClickedIcon: (App) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clickable{ onClickedIcon(app) }
    ) {
        Image(
            painter = painterResource(app.image),
            contentDescription = stringResource(app.name),
            modifier = Modifier
                .size(64.dp)
        )
        Text(
            stringResource(app.name),
            fontSize = 13.sp,
            color = Color.White
        )
    }
}