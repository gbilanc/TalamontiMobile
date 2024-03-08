package net.gibisoft.talamonti

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import net.gibisoft.talamonti.databinding.FragmentUtensileBinding
import net.gibisoft.talamonti.entities.Utensile

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class UtensileRecyclerViewAdapter(
    private val values: List<Utensile>
) : RecyclerView.Adapter<UtensileRecyclerViewAdapter.ViewHolder>() {

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
        holder.idView.text = item.codice
        holder.contentView.text = item.toString()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentUtensileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}