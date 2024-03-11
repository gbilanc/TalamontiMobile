package net.gibisoft.talamonti

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import net.gibisoft.talamonti.databinding.FragmentScaffaleListaBinding
import net.gibisoft.talamonti.entities.Scaffale
import net.gibisoft.talamonti.entities.ScaffaleController

/**
 * A simple [Fragment] subclass.
 * Use the [ScaffaleListaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScaffaleListaFragment : Fragment() {

    private val handler2 = object : ItemClickHandler<Scaffale> {
        override fun onItemClick(item: Scaffale) {
            (activity as MainActivity?)!!.onScaffaleSelected(item)
        }
    }
    private val lista: MutableList<Scaffale> = ArrayList()
    private var scaffaleAdapter = ScaffaleRecyclerViewAdapter(handler2)
    private var _binding: FragmentScaffaleListaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScaffaleListaBinding.inflate(inflater, container, false)
        binding.ibAddScaffale.setOnClickListener { addNewScaffale() }
        scaffaleAdapter.setValues(lista)
        binding.scaffaleRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.scaffaleRecyclerView.adapter = scaffaleAdapter
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        lista.clear()
        ScaffaleController.lista(context).forEach {
            lista.add(it)
        }
    }
    private fun addNewScaffale() {
        handler2.onItemClick(Scaffale())
    }

    companion object {
        fun newInstance() = ScaffaleListaFragment()
    }

}