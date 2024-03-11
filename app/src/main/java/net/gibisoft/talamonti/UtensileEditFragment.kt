package net.gibisoft.talamonti

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.gibisoft.talamonti.databinding.FragmentUtensileEditBinding
import net.gibisoft.talamonti.entities.Utensile
import net.gibisoft.talamonti.entities.UtensileController

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CODICE = "codice_utensile"

/**
 * A simple [Fragment] subclass.
 * Use the [UtensileEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UtensileEditFragment : Fragment() {
    private var utensile = Utensile()
    private var _binding: FragmentUtensileEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getString(CODICE)?.let { codice ->
                utensile = UtensileController.load(context, codice)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_utensile_edit, container, false)
        _binding = FragmentUtensileEditBinding.inflate(inflater, container, false)
        binding.editTextCodice.setText(utensile.codice)
        binding.editTextDescrizione.setText(utensile.descrizione)
        binding.editTextScaffale.setText(utensile.scaffale)
        binding.editTextPosizione.setText(utensile.posizione.toString())
        binding.ibHomepage.setOnClickListener { close() }
        binding.ibDeleteUtensile.setOnClickListener { delete() }
        binding.ibSaveUtensile.setOnClickListener { save() }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun close() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun save() {
        requireActivity().supportFragmentManager.popBackStack()
        TODO("Implementare salvataggio utensile")
    }

    private fun delete() {
        requireActivity().supportFragmentManager.popBackStack()
        TODO("Implementare cancellazione utensile")
    }

    companion object {
        @JvmStatic
        fun newInstance(codice: String) =
            UtensileEditFragment().apply {
                arguments = Bundle().apply {
                    putString(CODICE, codice)
                }
            }
    }
}