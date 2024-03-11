package net.gibisoft.talamonti

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.gibisoft.talamonti.databinding.FragmentScaffaleEditBinding
import net.gibisoft.talamonti.entities.Scaffale
import net.gibisoft.talamonti.entities.ScaffaleController


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CODICE = "codice_scaffale"

/**
 * A simple [Fragment] subclass.
 * Use the [ScaffaleEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScaffaleEditFragment : Fragment() {
    private var scaffale = Scaffale()
    private var _binding: FragmentScaffaleEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getString(CODICE)?.let { codice ->
                scaffale = ScaffaleController.load(context, codice)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScaffaleEditBinding.inflate(inflater, container, false)
        binding.editTextCodice.setText(scaffale.codice)
        binding.editTextIndirizzo.setText(scaffale.indirizzo)
        binding.editTextPorta.setText(scaffale.porta.toString())
        binding.ibHomepage.setOnClickListener { close() }
        binding.ibDeleteScaffale.setOnClickListener { delete() }
        binding.ibSaveScaffale.setOnClickListener { save() }
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
        TODO("Implementare salvataggio scaffale")
    }

    private fun delete() {
        requireActivity().supportFragmentManager.popBackStack()
        TODO("Implementare cancellazione scaffale")
    }

    companion object {
        @JvmStatic
        fun newInstance(codice: String) =
            ScaffaleEditFragment().apply {
                arguments = Bundle().apply {
                    putString(CODICE, codice)
                }
            }
    }
}