package casa.osmar.projectofinalmoviles.screen

import android.annotation.SuppressLint
import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import casa.osmar.projectofinalmoviles.data.model.Show

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(show: Show, navController: NavController, favoriteViewModel: FavoriteViewModel) {

    val favoriteShows by favoriteViewModel.favoriteShows.collectAsState()
    val isShowOnFavorites = favoriteShows.any { it.id == show.id }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = show.name,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresarr",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0d4634),
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    favoriteViewModel.addOrDeleteFavorite(show) // Agregar o eliminar de favoritos (la logica se hace en el view model)
                },
                containerColor = Color(0xFF0d4634),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = if (isShowOnFavorites) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isShowOnFavorites) "Eliminar favorito" else "Añadir favorito"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF062018))
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { // Ya que es Lazy Columne, tonces utilizamos item
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .height(300.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 16.dp
                    ),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0d4634))
                ) {
                    AsyncImage(
                        model = show.image?.original ?: "",
                        contentDescription = show.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    DetailRow(label = "Calificación:", value = show.rating?.average?.toString() ?: "Sin calificación")
                    DetailRow(label = "Géneros:", value = show.genres?.joinToString(", ") ?: "Sin especificación")
                    DetailRow(label = "Fecha de inicio:", value = show.premiered ?: "Sin especificación")
                    DetailRow(label = "Región:", value = show.network?.country?.name ?: "Sin especificación")
                    DetailRow(label = "Idioma:", value = show.language ?: "SIn especificar")

                    Spacer(modifier = Modifier.height(10.dp))

                    // Título de Descripción alineado a la izquierda y además justificado !!
                    Text(
                        text = "Descripción:",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = removeHtmlTags(show.summary ?: "No hay descripción para esta serie."),
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
}

// Función composable principalmente para para las características del show
@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween // Con un espacio en medio
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// Función para eliminar etiquetas HTML
fun removeHtmlTags(html: String): String {
    return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT).toString()
}

