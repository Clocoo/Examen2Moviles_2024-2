package casa.osmar.projectofinalmoviles.data.remote

import casa.osmar.projectofinalmoviles.data.model.Show
import retrofit2.Response
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// URL para la API de TVMaze
private const val BASE_URL = "https://api.tvmaze.com/"

//Instancia de Moshi para la serialización de JSON
private val moshi = com.squareup.moshi.Moshi.Builder()
    .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
    .build()

// Instancia de Retrofit
private val retrofit = retrofit2.Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

// Servicio de la API
interface TvMazeService {
    @GET("shows")
    suspend fun getShows(
        @Query("page") page: Int = 1
    ): Response<List<Show>>

    @GET("shows/{id}")
    suspend fun getShowById(
        @Path("id") id: Int
    ): Response<Show>
}

object TvMazeApi {
    val service: TvMazeService by lazy {
        retrofit.create(TvMazeService::class.java)
    }
}