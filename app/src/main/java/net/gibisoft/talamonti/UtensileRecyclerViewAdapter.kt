package net.gibisoft.talamonti

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import net.gibisoft.talamonti.databinding.FragmentUtensileBinding
import net.gibisoft.talamonti.entities.Utensile

/**
 * [RecyclerView.Adapter] that can display a [Utensile].
 */
class UtensileRecyclerViewAdapter : RecyclerView.Adapter<UtensileRecyclerViewAdapter.ViewHolder>() {

    private var values: List<Utensile> = mutableListOf()
    fun setValues(data: List<Utensile>) {
        data.also { values = it }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentUtensileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.text1.text = item.getCodiceAndDescrizione()
        holder.text2.text = item.getScaffaleAndPosizione()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentUtensileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val text1: TextView = binding.itemText1
        val text2: TextView = binding.itemText2

    }
}