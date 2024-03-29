package net.gibisoft.talamonti

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import net.gibisoft.talamonti.entities.Scaffale
import net.gibisoft.talamonti.entities.ScaffaleController
import net.gibisoft.talamonti.entities.Utensile
import net.gibisoft.talamonti.entities.UtensileController


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ScaffaleController.init_table(this)
        UtensileController.init_table(this)

        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.setLogo(R.mipmap.ic_launcher_round)
            it.setDisplayUseLogoEnabled(true)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_scaffali -> {
                val editFragment = ScaffaleListaFragment.newInstance()
                supportFragmentManager.commit {
                    replace(R.id.fragmentContainerView, editFragment)
                    setReorderingAllowed(true)
                    addToBackStack("scaffale_lista")
                }
                Log.i("MainActivity2.menu", "menu scaffali clicked!")
                true
            }

            R.id.menu_utensili -> {
                val editFragment = UtensileListaFragment.newInstance()
                supportFragmentManager.commit {
                    replace(R.id.fragmentContainerView, editFragment)
                    setReorderingAllowed(true)
                    addToBackStack("utensile_lista")
                }
                Log.i("MainActivity2.menu", "menu utensili clicked!")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onUtensileSelected(item: Utensile) {
        val editFragment = UtensileEditFragment.newInstance(item.codice)
        supportFragmentManager.commit {
            replace(R.id.fragmentContainerView, editFragment)
            setReorderingAllowed(true)
            addToBackStack("utensile_edit")
        }
    }

    fun onScaffaleSelected(item: Scaffale) {
        val editFragment = ScaffaleEditFragment.newInstance(item.codice)
        supportFragmentManager.commit {
            replace(R.id.fragmentContainerView, editFragment)
            setReorderingAllowed(true)
            addToBackStack("scaffale_edit")
        }
    }
}