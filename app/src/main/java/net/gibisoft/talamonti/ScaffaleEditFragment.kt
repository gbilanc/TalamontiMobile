package net.gibisoft.talamonti

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import net.gibisoft.talamonti.databinding.FragmentScaffaleEditBinding
import net.gibisoft.talamonti.entities.Scaffale
import net.gibisoft.talamonti.entities.ScaffaleController

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
        val scaffale = Scaffale(
            binding.editTextCodice.text.toString(),
            binding.editTextIndirizzo.text.toString(),
            Integer.parseInt(binding.editTextPorta.text.toString())
        )
        ScaffaleController.save(context, scaffale)
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
                    ScaffaleController.delete(context, binding.editTextCodice.text.toString())
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
            ScaffaleEditFragment().apply {
                arguments = Bundle().apply {
                    putString(CODICE, codice)
                }
            }
    }
}