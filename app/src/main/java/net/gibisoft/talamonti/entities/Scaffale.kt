package net.gibisoft.talamonti.entities

data class Scaffale(
    var codice: String = "",
    var indirizzo: String = "",
    var porta: Int = 9600
) {
    var cassetti: Array<Cassetto> = Array(20) { index ->
        Cassetto(codice, index + 1, 1, 0)
    }

    override fun toString(): String {
        return codice
    }

    fun getListaCassetti(): MutableList<Cassetto> {
        return cassetti.toMutableList()
    }
}

