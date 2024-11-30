package casa.osmar.projectofinalmoviles.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteShow::class], version = 1, exportSchema = false)
abstract class FavoritesShowsDatabase : RoomDatabase() {

    abstract fun favoriteShowDao(): FavoriteShowDao

    companion object {
        @Volatile
        private var INSTANCE: FavoritesShowsDatabase? = null

        fun getInstance(context: Context): FavoritesShowsDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    FavoritesShowsDatabase::class.java,
                    "favoriteShows_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
