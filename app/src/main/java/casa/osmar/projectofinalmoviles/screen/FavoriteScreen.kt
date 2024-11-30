package casa.osmar.projectofinalmoviles.screen

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import casa.osmar.projectofinalmoviles.database.FavoriteShow
import androidx.navigation.NavController

// Pantalla donde aparecerán las series o shows favorites del usuario
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteShows(
    onShowClick: (Int) -> Unit,
    onNavigateToHome: () -> Unit,
    favoriteViewModel: FavoriteViewModel
) {
    val favoriteShows by favoriteViewModel.favoriteShows.collectAsState() // Se observa para modificar la UI en tiempo real

    // Este podría decirse que es un init, se cargan los shows en favoritos
    LaunchedEffect(Unit) {
        favoriteViewModel.loadFavoriteShows()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Favoritos",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0d4634)
                )
            )
        },
        bottomBar = {
            NavigationBar(  // BottomNavigation de Material3 XD
                containerColor = Color(0xFF0d4634),
                contentColor = Color.White
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Inicio",
                            tint = Color.White
                        )
                    },
                    label = {
                        Text(
                            text = "Inicio",
                            color = Color.White
                        )
                    },
                    selected = false,
                    onClick = { onNavigateToHome() }, // Para ir a la vista de Inicio
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favoritoss",
                            tint = Color(0xFF0d4634)
                        )
                    },
                    label = {
                        Text(
                            text = "Favoritos",
                            color = Color.White
                        )
                    },
                    selected = true,
                    onClick = {  }, // Nada, ya que es la vista en la que se está ya
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = Color.Transparent,
                        indicatorColor = Color.White
                    )
                )
            }
        }
    ) { innerPadding ->
        if (favoriteShows.isEmpty()) {
            // Si no hay favoritos guardados, entonces se mostrará:
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF062018)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay series en Favoritos",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF062018))
                    .padding(innerPadding),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoriteShows) { show ->
                    ShowCard( // Básicamente el mismo ShowCard de nuestra vista Inicio
                        show = show,
                        onClick = { show.id?.let { onShowClick(it) } } // PARA IR AL SHOW SLECCIONADO
                    )
                }
            }
        }
    }
}