package com.watsidev.producto1.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.watsidev.producto1.data.App

@Composable
fun BottomApps(
    listApps: List<App>,
    onClickedIcon: (App) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = WindowInsets.navigationBars.asPaddingValues()
                    .calculateBottomPadding()
            ),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items(4) {
            AppIcons(
                app = listApps[it],
                onClickedIcon = onClickedIcon,
                modifier = Modifier
            )
        }
    }
}