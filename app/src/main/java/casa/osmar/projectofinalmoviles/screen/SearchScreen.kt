package casa.osmar.projectofinalmoviles.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import casa.osmar.projectofinalmoviles.data.model.Show

// Esta es una vista aparte, se decidió hacerlo así para mantener un mejor orden.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchShow(
    navController: NavController,
    shows: List<Show>,
    onShowClick: (Int) -> Unit
) {
    var searchQueryList by remember { mutableStateOf("") }
    var filteredTVShows by remember { mutableStateOf(shows) }

    LaunchedEffect(searchQueryList) {
        filteredTVShows = if (searchQueryList.isEmpty()) shows else shows.filter {
            it.name.contains(searchQueryList, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Series", // Título en la TopBar, igual que en la HomeScreen
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresarr",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0d4634),
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF062018))
        ) {
            SearchBar(
                query = searchQueryList,
                onQueryChange = { searchQueryList = it }, // Se va actualizando conforme se escribe
                placeholder = { Text("Buscar Series",
                    color = Color(0xFFFFFFFF)
                ) },
                active = true,
                onActiveChange = {},
                onSearch = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Buscar icono",
                        tint = Color(0xFFFFFFFF)
                    )
                },
                colors = SearchBarDefaults.colors(
                    containerColor = Color(0xFF062018),
                    inputFieldColors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = Color(0xFFFFFFFF),
                        focusedIndicatorColor = Color(0xFFFFFFFF),
                        unfocusedIndicatorColor = Color.White
                    )
                ),
                content = {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                    ) {
                        items(filteredTVShows) { show ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { show.id?.let { onShowClick(it) } }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = show.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}
