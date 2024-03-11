package net.gibisoft.talamonti

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import net.gibisoft.talamonti.databinding.FragmentUtensileListaBinding
import net.gibisoft.talamonti.entities.Utensile
import net.gibisoft.talamonti.entities.UtensileController

/**
 * A simple [Fragment] subclass.
 * Use the [UtensileListaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UtensileListaFragment : Fragment() {

    private val handler1 = object : ItemClickHandler<Utensile> {
        override fun onItemClick(item: Utensile) {
            (activity as MainActivity?)!!.onUtensileSelected(item)
        }
    }
    private val lista: MutableList<Utensile> = ArrayList()
    private var utensileAdapter = UtensileRecyclerViewAdapter(handler1)
    private var _binding: FragmentUtensileListaBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        lista.clear()
        UtensileController.lista(context).forEach {
            lista.add(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUtensileListaBinding.inflate(inflater, container, false)
        binding.ibAddUtensile.setOnClickListener { addNewUtensile() }
        binding.ibFindUtensile.setOnClickListener { applicaFiltro() }
        utensileAdapter.setValues(lista)
        binding.utensileRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.utensileRecyclerView.adapter = utensileAdapter
        return binding.root
    }

    private fun applicaFiltro() {
        val filtro = binding.textFinder.text.toString()
        val filtered =
            lista.filter { filtro.uppercase() in it.getCodiceAndDescrizione().uppercase() }
        utensileAdapter.setValues(filtered)
        utensileAdapter.notifyDataSetChanged()
    }

    private fun addNewUtensile() {
        handler1.onItemClick(Utensile())
    }

    companion object {
        fun newInstance() = UtensileListaFragment()
    }
}