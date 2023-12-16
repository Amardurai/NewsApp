package com.example.androidcleanarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidcleanarchitecture.data.model.response.NewsResponse
import com.example.androidcleanarchitecture.presentation.newsdetails.DetailScreen
import com.example.androidcleanarchitecture.presentation.newslist.ListComposeScreen
import com.example.androidcleanarchitecture.presentation.newslist.ListScreen
import com.example.androidcleanarchitecture.ui.theme.AndroidCleanArchitectureTheme
import com.example.androidcleanarchitecture.utils.NavDestinations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidCleanArchitectureTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NavDestinations.LIST_SCREEN
                    ) {
                        composable(NavDestinations.LIST_SCREEN) {
                            ListComposeScreen(navController = navController)
                        }
                        composable(NavDestinations.DETAIL_SCREEN) {
                                DetailScreen(newsId = 1, navController = navController)
                        }
                        composable("${NavDestinations.DETAIL_SCREEN}/{newsId}") {
                            it.arguments?.getString("newsId")?.let { newId ->
                                DetailScreen(newsId = newId.toInt(), navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidCleanArchitectureTheme {
        ListScreen(
            navController = rememberNavController(),
            newsList = NewsResponse.mock().articles ?: emptyList()
        )
    }
}