package com.example.mod21_final

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)

        val toolBar = findViewById<Toolbar>(R.id.details_toolbar)
        val description = findViewById<TextView>(R.id.details_description)
        val bundle = intent.extras
        if(bundle != null) {
            val film: Film = bundle.get("film") as Film
            // !!!!!!!!!!!!!!
            toolBar.title = film.title
            findViewById<AppCompatImageView>(R.id.details_poster).setImageResource(film.poster)
            description.text = film.description
        }


        toolBar.setOnMenuItemClickListener(){
            when (it.itemId) {
                R.id.menu_item_favorite -> {
                    val snackBar = Snackbar.make(description, "Example action clicked", Snackbar.LENGTH_LONG)
                    snackBar.setAction("Some Action"){
                        Toast.makeText(this, "SomeText", Toast.LENGTH_SHORT).show()
                    }
                    snackBar.setActionTextColor( getResources().getColor(R.color.colorAccent))
                    snackBar.show()
                    true
                }
                R.id.menu_item_later -> {
                    val snackBar = Snackbar.make(description, "Example action clicked", Snackbar.LENGTH_LONG)
                    snackBar.setAction("Watch it later"){
                        Toast.makeText(this, "More later", Toast.LENGTH_SHORT).show()
                    }
                    snackBar.setActionTextColor( getResources().getColor(R.color.colorAccent))
                    snackBar.show()
                    true

                }
                R.id.menu_item_selections -> {
                    Toast.makeText(this, "Подборки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
        //    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        //    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        //    insets
        //}

    }
}