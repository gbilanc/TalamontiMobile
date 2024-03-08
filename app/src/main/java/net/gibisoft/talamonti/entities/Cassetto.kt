package net.gibisoft.talamonti.entities

data class Cassetto(
    var scaffale: String = "",
    var posizione: Int = 0,
    var capacita: Int = 0,
    var numpezzi: Int = 0
) {

    fun isPieno(): Boolean {
        return numpezzi >= capacita
    }
}

