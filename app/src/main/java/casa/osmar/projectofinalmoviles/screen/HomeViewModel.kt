package casa.osmar.projectofinalmoviles.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import casa.osmar.projectofinalmoviles.data.model.Show
import casa.osmar.projectofinalmoviles.data.remote.TvMazeApi

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Para manejar \ gestionar el estado de la inmterfaz, además
// es acá donde principalmente manejaremos la comunicación con la API.
data class HomeUIState(
    val shows: List<Show> = emptyList() // Acá se guardan las series leídas de la API
)

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeUIState())
    val state: StateFlow<HomeUIState> get() = _state // La UI lo observa

    // Función para obtener los shows \ series desde la API
    fun fetchShows(page: Int = 1) {
        viewModelScope.launch {
            try {
                val response = TvMazeApi.service.getShows(page)
                if (response.isSuccessful) {
                    _state.value = HomeUIState(shows = response.body() ?: emptyList())
                }
            } catch (e: Exception) {
            }
        }
    }
}