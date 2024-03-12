package net.gibisoft.talamonti.entities

data class Utensile(

    var codice: String = "",
    var descrizione: String = "",
    var scaffale: String = "",
    var posizione: Int = 0
) {

    fun getCodiceAndDescrizione(): String {
        return "[$codice] $descrizione"
    }

    fun getScaffaleAndPosizione(): String {
        return "scaffale: $scaffale, cassetto: $posizione"
    }

}