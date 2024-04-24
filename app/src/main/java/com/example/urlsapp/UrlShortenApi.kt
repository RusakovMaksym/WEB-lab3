import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface UrlShortenApi {
    @GET
    fun shortenUrl(@Url apiUrl: String): Call<ShortenedUrlResponse>
}