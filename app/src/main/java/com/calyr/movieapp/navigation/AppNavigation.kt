package com.calyr.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.calyr.movieapp.screen.CinemaMap
import com.calyr.movieapp.screen.MovieDetailScreen
import com.calyr.movieapp.screen.MoviesScreen
import com.calyr.movieapp.viewmodel.MovieViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.MoviesScreen.route
    ) {
        composable(Screens.MoviesScreen.route) {
            val movieViewModel : MovieViewModel = hiltViewModel()

            MoviesScreen(
                onClick = {
                   movieId -> navController.navigate("${Screens.MovieDetailScreen.route}/${movieId}")
                },
                movieViewModel
            )
        }
        composable(
            route = "${Screens.MovieDetailScreen.route}/{movieId}",
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.StringType
                }
            )
        ) {
            MovieDetailScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                movieId = it.arguments?.getString("movieId")?:""
            )
        }
        composable(Screens.CinemaMapScreen.route) {
            CinemaMap()
        }
    }
}