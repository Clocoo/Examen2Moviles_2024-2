package casa.osmar.projectofinalmoviles.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
data class DetailScreen(val showId: Int)

@Serializable
object SearchScreen

@Serializable
object FavoriteScreen