package luis.sizzo.a20231130luis_sizzo_walmart_ranstand.api

import kotlinx.coroutines.flow.Flow
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.model.UIStateFlow

interface RepositoryApi {
    fun getCountries(): Flow<UIStateFlow>
}