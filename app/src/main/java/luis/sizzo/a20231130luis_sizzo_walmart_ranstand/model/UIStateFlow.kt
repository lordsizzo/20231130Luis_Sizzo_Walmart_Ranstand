package luis.sizzo.a20231130luis_sizzo_walmart_ranstand.model

sealed class UIStateFlow {
    object LOADING : UIStateFlow()
    class SUCCESS<T>(val response : T) : UIStateFlow()
    class ERROR(val error: Exception) : UIStateFlow()
}