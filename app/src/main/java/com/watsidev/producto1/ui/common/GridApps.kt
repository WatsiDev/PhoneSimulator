package com.watsidev.producto1.ui.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.watsidev.producto1.R
import com.watsidev.producto1.data.App

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GridApps(
    listApps: List<App>,
    onClickedIcon: (App) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item(
            span = { GridItemSpan(3) }
        ) {
            TimeWidget()
        }
        items(listApps) { app ->
            AppIcons(
                app = app,
                onClickedIcon = onClickedIcon,
            )
        }
        item {
            AppIcons(
                app = App(
                    name = R.string.youtube,
                    image = R.drawable.youtube,
                    route = "youtube_intent"
                ),
                onClickedIcon = onClickedIcon,
            )
        }
    }

}