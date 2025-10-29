package com.example.evaldylann

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.evaldylann.movies.ui.detail.MovieDetailRoute
import com.example.evaldylann.movies.ui.MoviesListRoute
import com.example.evaldylann.movies.ui.MoviesViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppRoot() }
    }
}
object Routes {
    const val Movies = "movies"
    const val MovieDetail = "movie/{id}"
}
@Composable
fun AppRoot() {
    val nav = rememberNavController()
    MaterialTheme {
        NavHost(navController = nav, startDestination = Routes.Movies) {
            composable(Routes.Movies) {
                val vm: MoviesViewModel = koinViewModel()
                MoviesListRoute(nav = nav, vm = vm)
            }

            composable(
                route = Routes.MovieDetail,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStack ->
                val id = backStack.arguments?.getString("id") ?: return@composable
                MovieDetailRoute(movieId = id, nav = nav)
            }

        }
    }
}