package casa.osmar.projectofinalmoviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import casa.osmar.projectofinalmoviles.navigation.AppNavigation
import casa.osmar.projectofinalmoviles.screen.FavoriteViewModel
import casa.osmar.projectofinalmoviles.screen.HomeViewModel
import casa.osmar.projectofinalmoviles.ui.theme.ProjectoFinalMovilesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val homeViewModel: HomeViewModel by viewModels()
            val favoriteViewModel: FavoriteViewModel by viewModels()
            homeViewModel.fetchShows()

            ProjectoFinalMovilesTheme {
                MainApp(homeViewModel, favoriteViewModel)
            }
        }
    }
}

@Composable
fun MainApp(homeViewModel: HomeViewModel, favoriteViewModel: FavoriteViewModel) {
    val navController = rememberNavController()
    val state = homeViewModel.state.collectAsState().value // Recordar que es la que permite que se actualice automáticamente la interfaz

    Scaffold { paddingValues ->
        AppNavigation(
            navController = navController,
            shows = state.shows,
            favoriteViewModel = favoriteViewModel,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}
