package net.gibisoft.talamonti

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import net.gibisoft.talamonti.databinding.FragmentScaffaleBinding
import net.gibisoft.talamonti.entities.Scaffale

/**
 * [RecyclerView.Adapter] that can display a [Scaffale].
 */
class ScaffaleRecyclerViewAdapter(
    private val values: List<Scaffale>
) : RecyclerView.Adapter<ScaffaleRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentScaffaleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.text1.text = item.codice
        holder.text2.text = item.toString()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentScaffaleBinding) : RecyclerView.ViewHolder(binding.root) {
        val text1: TextView = binding.itemText11
        val text2: TextView = binding.itemText12

    }

}