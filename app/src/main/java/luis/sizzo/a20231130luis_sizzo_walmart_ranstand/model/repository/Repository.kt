package luis.sizzo.a20231130luis_sizzo_walmart_ranstand.model.repository

import kotlinx.coroutines.flow.*
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.api.RepositoryApi
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.utils.BASE_URL
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.model.UIStateFlow
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.model.remote.remoteAPIConnection
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryImpl: RepositoryApi {
     override fun getCountries() = flow {
        emit(UIStateFlow.LOADING)
        try {
            val respose = initRetrofit().getCategories()
            if (respose.isSuccessful) {
                respose.body()?.let { result ->
                    emit(UIStateFlow.SUCCESS(result))
                } ?: throw Exception("Error null")
            } else {
                throw Exception("Error failure")
            }
        } catch (e: Exception) {
            emit(UIStateFlow.ERROR(e))
        }
    }

    private fun initRetrofit(): remoteAPIConnection {
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(remoteAPIConnection::class.java)
    }
}