package net.gibisoft.talamonti

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import net.gibisoft.talamonti.entities.Scaffale

class ScaffaleAdapter(
    context: Context,
    @LayoutRes val layoutResourceId: Int,
    @IdRes val textViewResourceId: Int,
    val scaffali: List<Scaffale>
) :
    ArrayAdapter<Scaffale>(context, layoutResourceId, textViewResourceId, scaffali) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return buildView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return buildView(position, convertView, parent)
    }

    private fun buildView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            convertView ?: LayoutInflater.from(context).inflate(layoutResourceId, parent, false)
        view.findViewById<TextView>(textViewResourceId).text = scaffali[position].codice
        return view
    }
}