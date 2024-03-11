package net.gibisoft.talamonti

interface ItemClickHandler<in T> {
    fun onItemClick(item: T)
}