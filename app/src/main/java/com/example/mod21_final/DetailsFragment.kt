package com.example.mod21_final

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment() {

    //********111111111111111111111111111111111111
    lateinit  var toolbar: Toolbar
    lateinit var description: TextView
    lateinit var details_poster : AppCompatImageView
    lateinit var film: Film

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //***********11111111111111111111111111111111111
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        toolbar = view.findViewById<Toolbar>(R.id.details_toolbar)
        description = view.findViewById(R.id.details_description)
        details_poster = view.findViewById<AppCompatImageView>(R.id.details_poster)

        val details_fab_favorites = view.findViewById<FloatingActionButton>(R.id.details_fab_favorites)
        val details_fab_share = view.findViewById<FloatingActionButton>(R.id.details_fab_share)

        val bundle = arguments
        if(bundle != null) {
            film = bundle.getParcelable<Film>("film")  as Film

            toolbar.title = film.title
            details_poster.setImageResource(film.poster)
            description.text = film.description

            details_fab_favorites.setImageResource(
                if (film.isInFavorites) R.drawable.ic_baseline_favorite_24
                else R.drawable.ic_baseline_favorite_border_24
            )
            details_fab_favorites.setOnClickListener {
                if (!film.isInFavorites) {
                    details_fab_favorites.setImageResource(R.drawable.ic_baseline_favorite_24)
                    film.isInFavorites = true
                } else {
                    details_fab_favorites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    film.isInFavorites = false
                }
            }

            details_fab_share.setOnClickListener {
                //Создаем интент
                val intent = Intent()
                //Указываем action с которым он запускается
                intent.action = Intent.ACTION_SEND
                //Кладем данные о нашем фильме
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out this film: ${film.title} \n\n ${film.description}"
                )
                //Указываем MIME тип, чтобы система знала, какое приложения предложить
                intent.type = "text/plain"
                //Запускаем наше активити
                startActivity(Intent.createChooser(intent, "Share To:"))
            }

        }

        toolbar.setOnMenuItemClickListener() {
            when (it.itemId) {
                R.id.menu_item_favorite -> {
                    val snackBar =
                        Snackbar.make(description, "Example action clicked", Snackbar.LENGTH_LONG)
                    snackBar.setAction("Some Action") {
                        Toast.makeText(view.context, "SomeText", Toast.LENGTH_SHORT).show()
                    }
                    snackBar.setActionTextColor(getResources().getColor(R.color.colorAccent))
                    snackBar.show()
                    true
                }
                R.id.menu_item_later -> {
                    val snackBar =
                        Snackbar.make(description, "Example action clicked", Snackbar.LENGTH_LONG)
                    snackBar.setAction("Watch it later") {
                        Toast.makeText(view.context, "More later", Toast.LENGTH_SHORT).show()
                    }
                    snackBar.setActionTextColor(getResources().getColor(R.color.colorAccent))
                    snackBar.show()
                    true
                }
                R.id.menu_item_selections -> {
                    Toast.makeText(view.context, "Подборки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }

        }
        //******************************


        return view //inflater.inflate(R.layout.fragment_details, container, false)
    }
}