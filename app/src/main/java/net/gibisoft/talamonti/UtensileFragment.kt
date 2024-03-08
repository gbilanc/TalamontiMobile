package net.gibisoft.talamonti

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.gibisoft.talamonti.entities.Utensile
import net.gibisoft.talamonti.entities.UtensileController

/**
 * A fragment representing a list of Items.
 */
class UtensileFragment : Fragment() {

    private var columnCount = 1
    private val ITEMS: MutableList<Utensile> = java.util.ArrayList()
    private val ITEM_MAP: MutableMap<String, Utensile> = java.util.HashMap()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        UtensileController.lista(context).forEach {
            ITEMS.add(it)
            ITEM_MAP[it.codice] = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_utensile_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = UtensileRecyclerViewAdapter(ITEMS)
            }
        }
        return view
    }

}