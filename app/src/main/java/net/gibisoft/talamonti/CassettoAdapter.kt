package net.gibisoft.talamonti

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
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
    val ledGreen = ContextCompat.getDrawable(context, R.drawable.green_led_on)
    val ledRed = ContextCompat.getDrawable(context, R.drawable.red_led_on)
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
                view.findViewById<ImageView>(R.id.imageViewLed).setImageDrawable(ledRed)
            } else {
                it.isClickable = false
                it.isEnabled = true
                view.findViewById<ImageView>(R.id.imageViewLed).setImageDrawable(ledGreen)
            }
        }
        return view
    }
}