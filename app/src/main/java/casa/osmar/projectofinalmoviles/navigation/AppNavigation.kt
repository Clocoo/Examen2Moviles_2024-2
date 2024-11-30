package casa.osmar.projectofinalmoviles.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import casa.osmar.projectofinalmoviles.data.model.Show
import casa.osmar.projectofinalmoviles.screen.DetailsScreen
import casa.osmar.projectofinalmoviles.screen.FavoriteShows
import casa.osmar.projectofinalmoviles.screen.FavoriteViewModel
import casa.osmar.projectofinalmoviles.screen.SearchShow
import casa.osmar.projectofinalmoviles.screen.ShowList

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    shows: List<Show>,
    favoriteViewModel: FavoriteViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeScreen
    ) {
        // La HomeScreen con todas las series, vista inicial
        composable<HomeScreen> {
            ShowList(
                shows = shows.take(50), // Muestra solo los primeros 50 shows
                onShowClick = { showId ->
                    navController.navigate(DetailScreen(showId))
                },
                navController = navController,
                onNavigateToFavorites = {
                    navController.navigate(FavoriteScreen)
                }
            )
        }

        composable<SearchScreen> {
            SearchShow(
                navController = navController,
                shows = shows,
                onShowClick = { showId ->
                    navController.navigate(DetailScreen(showId))
                }
            )
        }

        composable<DetailScreen> { backStackEntry ->
            val showId = backStackEntry.arguments?.getInt("showId") ?: -1
            val show = shows.find { it.id == showId }
            show?.let {
                DetailsScreen(show = it, navController = navController, favoriteViewModel = favoriteViewModel) // Mandar el FavoriteViewModel desde la Main Activity hacía acá
            }
        }

        composable<FavoriteScreen> {
            FavoriteShows(
                onNavigateToHome = {
                    navController.navigate(HomeScreen)
                },
                favoriteViewModel = favoriteViewModel,
                onShowClick = { showId ->
                    navController.navigate(DetailScreen(showId))
                }
            )
        }
    }
}
