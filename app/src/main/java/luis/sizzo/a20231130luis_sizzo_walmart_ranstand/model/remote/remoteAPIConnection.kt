package luis.sizzo.a20231130luis_sizzo_walmart_ranstand.model.remote

import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.utils.END_POINT_COUNTRIES
import retrofit2.*
import retrofit2.http.*

interface remoteAPIConnection{

    @GET(END_POINT_COUNTRIES)
    suspend fun getCategories(
    ): Response<List<Coutries>>

}