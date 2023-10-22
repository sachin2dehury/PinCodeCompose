package github.sachin2dehury.pincodecompose

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PinService {

    @GET("{pin}")
    suspend fun getPinData(@Path("pin") pin: String): Response<PinResponse>

    companion object {
        const val BASE_URL = "https://api.zippopotam.us/in/"
    }
}
