package com.example.mod21_final



import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() ,OnItemClickListener {
    private val filmsDataBase:List<Film> = listOf(
        Film("film1_title",R.drawable.poster1 , "film1_desc"),
        Film("film2_title",R.drawable.poster2 , "film2_desc"),
        Film("film3_title",R.drawable.poster3 , "film3_desc"),
        Film("film4_title",R.drawable.poster4 , "film4_desc"),
        Film("film5_title",R.drawable.poster5 , "film5_desc"),
        Film("film6_title",R.drawable.poster6 , "film6_desc"),
        Film("film7_title",R.drawable.poster7 , "film7_desc"),
        Film("film8_title",R.drawable.poster8 , "film8_desc"),
        Film("film89title",R.drawable.poster9 , "film9_desc"),
        Film("film10_title",R.drawable.poster10 , "film10_desc"),
    )

    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val topAppBar  = findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.tool_item_settings -> {
                    Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }


        val bottomNavigation : BottomNavigationView = findViewById(R.id.bottom_navigation)
        // bottom_navigation.setOnNavigationItemSelectedListener {
        bottomNavigation.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.menu_item_favorite -> {
                    Toast.makeText(this, "Избранное", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_item_later -> {
                    Toast.makeText(this, "Посмотреть похже", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_item_selections -> {
                    Toast.makeText(this, "Подборки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        //**********************************************************************************************

        filmsAdapter = FilmListRecyclerAdapter(this)

        val decorator = TopSpacingItemDecoration(8)

        val mainRecycler:RecyclerView = findViewById(R.id.main_recycler)
        mainRecycler.apply{
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(decorator)
        }

        filmsAdapter.addItems(filmsDataBase)


        // *************************************************************

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onItemClick(position: Int) {

        //Toast.makeText(this, "Clicked item at position $position", Toast.LENGTH_SHORT).show()
        val bundle = Bundle().apply {
            putParcelable("film", filmsDataBase[position])
        }
        //bundle.putParcelable("film", filmsDataBase[position])
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)

    }

}