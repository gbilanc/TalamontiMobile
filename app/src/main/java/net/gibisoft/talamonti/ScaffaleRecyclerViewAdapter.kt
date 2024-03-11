package net.gibisoft.talamonti

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.gibisoft.talamonti.databinding.FragmentScaffaleItemBinding
import net.gibisoft.talamonti.entities.Scaffale

/**
 * [RecyclerView.Adapter] that can display a [Scaffale].
 */
class ScaffaleRecyclerViewAdapter(
    private val handler: ItemClickHandler<Scaffale>
) : RecyclerView.Adapter<ScaffaleRecyclerViewAdapter.ViewHolder>() {

    private var values: List<Scaffale> = mutableListOf()
    fun setValues(data: List<Scaffale>) {
        data.also { values = it }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentScaffaleItemBinding.inflate(
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
        holder.ibEdit.setOnClickListener { handler.onItemClick(item) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentScaffaleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val text1: TextView = binding.itemText11
        val text2: TextView = binding.itemText12
        val ibEdit: ImageButton = binding.ibEditScaffale
    }

}