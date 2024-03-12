package net.gibisoft.talamonti

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import net.gibisoft.talamonti.databinding.FragmentUtensileEditBinding
import net.gibisoft.talamonti.entities.Cassetto
import net.gibisoft.talamonti.entities.Scaffale
import net.gibisoft.talamonti.entities.ScaffaleController
import net.gibisoft.talamonti.entities.Utensile
import net.gibisoft.talamonti.entities.UtensileController
import java.util.Arrays


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CODICE = "codice_utensile"

/**
 * A simple [Fragment] subclass.
 * Use the [UtensileEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UtensileEditFragment : Fragment() {
    private var utensile = Utensile()
    private var scaffale = Scaffale()
    private lateinit var scaffaleAdapter: ArrayAdapter<Scaffale>
    private lateinit var cassettoAdapter: ArrayAdapter<Cassetto>
    private var _binding: FragmentUtensileEditBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        scaffaleAdapter = ArrayAdapter(
            context,
            R.layout.scaffale_spinner_item, R.id.textView5,
            ScaffaleController.lista(context)
        )
        cassettoAdapter = ArrayAdapter(
            context,
            R.layout.cassetto_spinner_item, R.id.textView6,
            scaffale.getListaCassetti()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getString(CODICE)?.let { codice ->
                utensile = UtensileController.load(context, codice)
                scaffale = ScaffaleController.load(context, utensile.scaffale)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUtensileEditBinding.inflate(inflater, container, false)
        binding.editTextCodice.setText(utensile.codice)
        binding.editTextDescrizione.setText(utensile.descrizione)
        binding.scaffaleSpinner.adapter = scaffaleAdapter
        binding.scaffaleSpinner.onItemSelectedListener = selectionHandler
        binding.scaffaleSpinner.setSelection(scaffaleAdapter.getPosition(scaffale))
        binding.cassettoSpinner.adapter = cassettoAdapter
        binding.cassettoSpinner.setSelection(utensile.posizione)
        binding.ibHomepage.setOnClickListener { close() }
        binding.ibDeleteUtensile.setOnClickListener { delete() }
        binding.ibSaveUtensile.setOnClickListener { save() }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private val selectionHandler = object : OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (parent != null) {
                cassettoAdapter.clear()
                scaffale = parent.selectedItem as Scaffale
                scaffale.cassetti.forEach { cassetto -> cassettoAdapter.add(cassetto) }
                cassettoAdapter.notifyDataSetChanged()
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }

    private fun close() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun save() {
        val utensile = Utensile(
            binding.editTextCodice.text.toString(),
            binding.editTextDescrizione.text.toString(),
            binding.scaffaleSpinner.selectedItem.toString(),
            binding.cassettoSpinner.selectedItemPosition
        )
        UtensileController.save(context, utensile)
        Toast.makeText(activity, getString(R.string.message1), Toast.LENGTH_SHORT).show();
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun delete() {
        deleteDialog().show()
    }

    private fun deleteDialog(): AlertDialog {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage(getString(R.string.message2))
            .setCancelable(true)
            .setPositiveButton("Si") { dialog, id ->
                run {
                    UtensileController.delete(context, binding.editTextCodice.text.toString())
                    requireActivity().supportFragmentManager.popBackStack()
                }
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        return dialogBuilder.create()
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