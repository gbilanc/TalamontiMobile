package net.gibisoft.talamonti

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.gibisoft.talamonti.entities.Scaffale
import net.gibisoft.talamonti.entities.ScaffaleController
import java.util.ArrayList
import java.util.HashMap

/**
 * A fragment representing a list of Items.
 */
class ScaffaleFragment : Fragment() {

    private var columnCount = 1
    private val ITEMS: MutableList<Scaffale> = ArrayList()
    private val ITEM_MAP: MutableMap<String, Scaffale> = HashMap()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ScaffaleController.lista(context).forEach {
            ITEMS.add(it)
            ITEM_MAP[it.codice] = it
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scaffale_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = ScaffaleRecyclerViewAdapter(ITEMS)
            }
        }
        return view
    }

}