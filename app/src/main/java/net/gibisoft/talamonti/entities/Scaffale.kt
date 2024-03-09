package net.gibisoft.talamonti.entities

data class Scaffale(
    var codice: String = "",
    var indirizzo: String = "",
    var porta: Int = 9600
) {
    var cassetti = arrayOfNulls<Cassetto>(20)

}
