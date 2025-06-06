package com.watsidev.producto1.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.watsidev.producto1.R
import com.watsidev.producto1.data.App
import com.watsidev.producto1.data.Datasource.listApps
import com.watsidev.producto1.ui.common.AppIcons
import com.watsidev.producto1.ui.common.BottomApps
import com.watsidev.producto1.ui.common.GoogleSearch
import com.watsidev.producto1.ui.common.GridApps
import com.watsidev.producto1.ui.common.TimeWidget

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    listApps: List<App>,
    onClickedIcon: (App) -> Unit,
    viewModel: HomeViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            painterResource(R.drawable.homebg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        HomeApp(
            listApps = listApps,
            onClickedIcon = onClickedIcon,
            valueChanged = { query ->
                viewModel.updateSearchQuery(query)
            },
            context = context,
            value = uiState.value.searchQuery,
            googleSearch = { query, context ->
                viewModel.GoogleSearch(query, context)
            }
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeApp(
    listApps: List<App>,
    onClickedIcon: (App) -> Unit,
    valueChanged: (String) -> Unit,
    context: Context,
    value: String,
    googleSearch: (String, Context) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        GoogleSearch(
            value = value,
            valueChanged = { valueChanged(it) },
            context = context,
            onQuerySearch = { query, context ->
                googleSearch(query, context)
            }
        )
        GridApps(
            listApps = listApps,
            onClickedIcon = onClickedIcon,
            modifier = Modifier
                .padding(top = 18.dp)
                .weight(1f)
        )
        BottomApps(
            listApps = listApps,
            onClickedIcon = onClickedIcon,
        )
    }
}