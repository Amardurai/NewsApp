package com.example.androidcleanarchitecture.presentation.newsdetails

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.androidcleanarchitecture.data.model.response.NewsResponse
import com.example.androidcleanarchitecture.domain.model.News
import com.example.androidcleanarchitecture.presentation.base.AppComposable
import com.example.androidcleanarchitecture.presentation.composable.WebPageView
import com.example.androidcleanarchitecture.ui.theme.AndroidCleanArchitectureTheme

@Composable
fun DetailScreen(
    newsId: Int,
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val showSmartMode by viewModel.showSmartMode.observeAsState(initial = true)
    viewModel.getNewsById(newsId)

    AppComposable(viewModel = viewModel, content = {
        DetailScreen(
            navController = navController,
            news = viewModel.news.value!!,
            showSmartMode = showSmartMode

        ) { viewModel.toggleMode() }
    }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    news: News,
    showSmartMode: Boolean,
    toggleMode: () -> Unit
) {

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = news?.title ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
            actions = {
                TextButton(onClick = { toggleMode }) {
                    Text(text = if (showSmartMode) "WEB" else "SMART", color = Color.White)
                }
            })
    }) {
        news?.let {
            if (showSmartMode) {
                SmartView(news = it)
            } else {
                WebPageView(urlToRender = it.url)
            }
        } ?: run {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsPreview() {
    AndroidCleanArchitectureTheme {
        DetailScreen(
            navController = rememberNavController(),
            news = NewsResponse.mock().articles?.firstOrNull()!!,
            showSmartMode = true,
            toggleMode = {}
        )
    }
}