package net.gibisoft.talamonti

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import net.gibisoft.talamonti.entities.CassettoController
import net.gibisoft.talamonti.entities.ScaffaleController
import net.gibisoft.talamonti.entities.UtensileController


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ScaffaleController.init_table(this)
        CassettoController.init_table(this)
        UtensileController.init_table(this)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            R.id.menu_scaffali -> {
                supportFragmentManager.commit {
                    replace<ScaffaleFragment>(R.id.fragmentContainerView)
                    setReorderingAllowed(true)
                    addToBackStack("scaffale")
                }
                Log.i("MainActivity2.menu","menu scaffali clicked!")
                true
            }
            R.id.menu_utensili -> {
                supportFragmentManager.commit {
                    replace<UtensileFragment>(R.id.fragmentContainerView)
                    setReorderingAllowed(true)
                    addToBackStack("utensile")
                }
                Log.i("MainActivity2.menu","menu utensili clicked!")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}