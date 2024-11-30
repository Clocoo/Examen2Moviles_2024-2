package casa.osmar.projectofinalmoviles.screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import casa.osmar.projectofinalmoviles.data.model.Show
import casa.osmar.projectofinalmoviles.data.remote.TvMazeApi
import casa.osmar.projectofinalmoviles.database.FavoritesShowsDatabase
import casa.osmar.projectofinalmoviles.database.FavoriteShow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    // Se utiliza application para así acceder a la base de datos.

    private val favoriteDao = FavoritesShowsDatabase.getInstance(application).favoriteShowDao()
    private val tvMazeService = TvMazeApi.service

    private val _favoriteShows = MutableStateFlow<List<Show>>(emptyList())
    val favoriteShows: StateFlow<List<Show>> get() = _favoriteShows
    // En este caso, contamos con 4 funciones, agregar, obtener, cargar y eliminar

    // Agregar un show a favoritos
    fun addToFavorites(show: Show) {
        viewModelScope.launch {
            val favorite = show.id?.let {
                FavoriteShow( // Esto es lo que se guardará en la database creada
                    id = it,
                    name = show.name,
                    image = show.image?.original,
                    summary = show.summary
                )
            }
            if (favorite != null) {
                favoriteDao.insertFavorite(favorite)
            }
        }
    }

    // Para obtener las IDs de los shows favoritos desde la base de datos, es el dato que nos importa
    suspend fun getFavoriteIds(): List<Int> {
        return favoriteDao.getFavorites().map { it.id }
    }

    // Se cargan los detalles completos de los shows favoritos usando las IDs
    fun loadFavoriteShows() {
        viewModelScope.launch {
            val favoriteIds = getFavoriteIds()
            val shows = mutableListOf<Show>()
            for (id in favoriteIds) {
                val response = tvMazeService.getShowById(id)
                if (response.isSuccessful) {
                    response.body()?.let { shows.add(it) }
                }
            }
            _favoriteShows.value = shows
        }
    }

    // Función para alternar entre agregar y eliminar un favorito
    fun addOrDeleteFavorite(show: Show) {
        viewModelScope.launch {
            val favoriteExist = _favoriteShows.value.find { it.id == show.id }
            if (favoriteExist != null) {
                // Eliminar de favoritos
                show.id?.let { favoriteDao.removeFavorite(it) }
                _favoriteShows.value = _favoriteShows.value.filter { it.id != show.id }
            } else {
                // Agregar a favoritos
                addToFavorites(show)
                _favoriteShows.value += show
            }
        }
    }
}
