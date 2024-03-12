package net.gibisoft.talamonti

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import net.gibisoft.talamonti.entities.Cassetto

class CassettoAdapter(
    context: Context,
    @LayoutRes val layoutResourceId: Int,
    @IdRes val textViewResourceId: Int,
    val cassetti: List<Cassetto>
) :
    ArrayAdapter<Cassetto>(context, layoutResourceId, textViewResourceId, cassetti) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return buildView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return buildView(position, convertView, parent)
    }

    private fun buildView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            convertView ?: LayoutInflater.from(context).inflate(layoutResourceId, parent, false)
        view.findViewById<TextView>(textViewResourceId).let {
            it.text = cassetti[position].toString()
            if (cassetti[position].isPieno()) {
                it.isClickable = true
                it.isEnabled = false
                it.setBackgroundColor(ContextCompat.getColor(context, R.color.cassetto_pieno))
            } else {
                it.isClickable = false
                it.isEnabled = true
                it.setBackgroundColor(ContextCompat.getColor(context, R.color.cassetto_vuoto))
            }
        }
        return view
    }
}