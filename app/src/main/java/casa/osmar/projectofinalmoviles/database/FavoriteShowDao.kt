package casa.osmar.projectofinalmoviles.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteShowDao {
    @Insert
    suspend fun insertFavorite(show: FavoriteShow)

    @Query("SELECT * FROM favorite_shows")
    suspend fun getFavorites(): List<FavoriteShow>

    @Query("DELETE FROM favorite_shows WHERE id = :id")
    suspend fun removeFavorite(id: Int)
}