package luis.sizzo.a20231130luis_sizzo_walmart_ranstand.model.remote


data class Coutries(
    val capital: String,
    val code: String,
    val currency: Currency,
    val flag: String,
    val language: Language,
    val name: String,
    val region: String
)


data class Currency(
    val code: String,
    val name: String,
    val symbol: String
)

data class Language(
    val code: String,
    val name: String
)
