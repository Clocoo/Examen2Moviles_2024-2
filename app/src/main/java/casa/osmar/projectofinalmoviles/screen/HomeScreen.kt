package casa.osmar.projectofinalmoviles.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import casa.osmar.projectofinalmoviles.data.model.Show
import casa.osmar.projectofinalmoviles.ui.theme.ProjectoFinalMovilesTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import casa.osmar.projectofinalmoviles.navigation.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowList(
    shows: List<Show>,
    onShowClick: (Int) -> Unit,
    navController: NavController,
    onNavigateToFavorites: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Series",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            )
                        )
                        // Este texto lo agregué para diferencias las versiones
                        Text(
                            text = "v27.11.24",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.White
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0d4634),
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(SearchScreen)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color.White
                        )
                    }
                }
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
                            tint = Color(0xFF0d4634)
                        )
                    },
                    label = {
                        Text(
                            text = "Inicio",
                            color = Color.White
                        )
                    },
                    selected = true,
                    onClick = { /* Ninguna acción ya que es la vista actual */ },
                    colors = NavigationBarItemDefaults.colors( // Con esto cambio el color del ícono y el botón
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = Color.Transparent,
                        indicatorColor = Color.White
                    )
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favs XD",
                            tint = Color.White
                        )
                    },
                    label = {
                        Text(
                            text = "Favoritos",
                            color = Color.White
                        )
                    },
                    selected = false,
                    onClick = { onNavigateToFavorites() } // Se navega a la vista de favoritosss
                )
            }
        }
    ) { innerPadding ->
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
            items(shows) { show ->
                ShowCard( // Se utiliza la función de ShowCards para mostrar las Series / Shows
                    show = show,
                    onClick = { show.id?.let { onShowClick(it) } } // Se llama al manejador de clics con el ID del show
                )
            }
        }
    }
}

@Composable
fun ShowCard(
    show: Show,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.55f)
            .padding(0.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp // Parámetro especial para que la card se vea mejor
        ),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0d4634))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            show.image?.let {
                AsyncImage( // Gracias a Coil podemos cargar imágenes desde URLs
                    model = it.medium,
                    contentDescription = show.name,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(0.dp),
                    contentScale = ContentScale.FillBounds,
                )
            }
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = show.name,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    color = Color.White,
                    maxLines = 1, // Para máximo 1 línea y que lo demas sean puntos suspensivos
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                show.genres?.joinToString(", ")?.let {
                    Text(
                        text = "Géneros: $it",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.sp
                        ),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                val ratingText = show.rating?.average?.let { rating ->
                    "${"%.1f".format(rating)}"
                } ?: "0.0"

                Text(
                    text = "Clasificación: $ratingText",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp
                    ),
                    color = Color.White
                )
            }
        }
    }
}