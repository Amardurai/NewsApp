package com.example.androidcleanarchitecture.presentation.newslist

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.androidcleanarchitecture.R
import com.example.androidcleanarchitecture.data.model.response.NewsResponse
import com.example.androidcleanarchitecture.domain.model.News
import com.example.androidcleanarchitecture.presentation.base.AppComposable
import com.example.androidcleanarchitecture.ui.theme.AndroidCleanArchitectureTheme
import com.example.androidcleanarchitecture.utils.NavDestinations

@Composable
fun ListComposeScreen(
    navController: NavController,
    viewModel: ListViewModel = hiltViewModel()
) {
    val newsList by viewModel.newsList.observeAsState(initial = emptyList())
    viewModel.getNewsList()
    val cx = LocalContext.current
    AppComposable(
        viewModel = viewModel,
        content = { ListScreen(navController, newsList) }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavController,
    newsList: List<News>,
) {
    val cx = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cryptocurrency news") },
            )
        }
    )
    {
        LazyColumn {
            items(newsList) { news ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("${NavDestinations.DETAIL_SCREEN}/${news.id}")
                        },
                ) {
                    Column {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f),
                            painter = rememberImagePainter(
                                data = news.urlToImage,
                                builder = {
                                    placeholder(R.drawable.ic_launcher_foreground)
                                    error(R.drawable.ic_launcher_foreground)
                                }
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth
                        )
                        Column(Modifier.padding(8.dp)) {
                            Text(
                                news.title ?: "",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    AndroidCleanArchitectureTheme {
        ListScreen(
            navController = rememberNavController(),
            newsList = NewsResponse.mock().articles ?: emptyList()
        )
    }
}