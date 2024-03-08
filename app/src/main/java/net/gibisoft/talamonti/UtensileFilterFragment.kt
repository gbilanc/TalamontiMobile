package net.gibisoft.talamonti

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.gibisoft.talamonti.entities.Utensile
import net.gibisoft.talamonti.entities.UtensileController

/**
 * A simple [Fragment] subclass.
 * Use the [UtensileFilterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UtensileFilterFragment : Fragment() {

    private val lista: MutableList<Utensile> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private var utensileAdapter = UtensileRecyclerViewAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        UtensileController.lista(context).forEach {
            lista.add(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_utensile_filter, container, false)
        val ibFind = view.findViewById<ImageButton>(R.id.ibFindUtensile)
        var textFind = view.findViewById<EditText>(R.id.textFinder)
        ibFind.setOnClickListener { applicaFiltro(textFind.text.toString()) }
        recyclerView = view.findViewById(R.id.utensileRecyclerView)!!
        recyclerView.layoutManager = LinearLayoutManager(context)
        utensileAdapter.setValues(lista)
        recyclerView.adapter = utensileAdapter
        return view
    }

    fun applicaFiltro(filtro: String) {
        val filtered = lista.filter { filtro.uppercase() in it.getCodiceAndDescrizione().uppercase() }
        utensileAdapter.setValues(filtered)
        utensileAdapter.notifyDataSetChanged()
    }
}