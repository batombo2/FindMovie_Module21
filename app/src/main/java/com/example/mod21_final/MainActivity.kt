package com.example.mod21_final




import android.os.Bundle
import android.view.MenuItem

import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity()  {
    val filmsDataBase:List<Film> = listOf(
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

    // ?????
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //************************************************************

        //FragmentActivity.getSupportFragmentManager()//
        getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.fragment_placeholder, GreatingFragment())     // HomeFragment()
           // .addToBackStack(null)
            .commit()
        
        //***********************************************************

        val topAppBar  = findViewById<MaterialToolbar>(R.id.topAppBar)

        /*
        val myListener1 = object:Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                .....
            }
        }
         */
        /*
        val myListener1 = Toolbar.OnMenuItemClickListener {
            when (it.itemId) {
                R.id.tool_item_settings -> {
                    Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        topAppBar.setOnMenuItemClickListener(myListener1)
         */

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
                R.id.menu_item_home -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    //В первом параметре, если фрагмент не найден и метод вернул null, то с помощью
                    //элвиса мы вызываем создание нового фрагмента
                    changeFragment( fragment?: HomeFragment(), tag)
                    true
                }

                R.id.menu_item_favorite -> {
                    val tag = "favorites"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: FavoritesFragment(), tag)
                    true
                }

                R.id.menu_item_later -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: WatchLaterFragment(), tag)
                    //Toast.makeText(this, "Посмотреть похже", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_item_selections -> {
                    val tag = "selections"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: SelectionFragment(), tag)
                    //Toast.makeText(this, "Подборки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // Perform an action 2 seconds after activity starts using coroutines
        lifecycleScope.launch {
            delay(2000) // Wait for 2 seconds

            val tag = "home"
            val fragment = checkFragmentExistence(tag)
            changeFragment( fragment?: HomeFragment(), tag)
        }

    }

    //Ищем фрагмент по тегу, если он есть то возвращаем его, если нет, то null
    private fun checkFragmentExistence(tag: String): Fragment? =
        supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .addToBackStack(null)
            .commit()
    }


    // загрузка фрагмента DetailsFragment во вложенный FrameLayout
    // вызывается из фрагмента FavoritesFragment(который подгрузится тот же FrameLayout)
    fun launchDetailsFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable("film", film)
        val fragment = DetailsFragment()
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
            )
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()

        // **********************************************************************************
        //   Back button  --- AlertDialog
        // Create a callback for the back button press
        /*
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //
            }
        }
         */
        // Add the callback to the OnBackPressedDispatcher
        //onBackPressedDispatcher.addCallback(this, callback)
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        // val msg = "backStackEntryCount = ${supportFragmentManager.backStackEntryCount}"
        if (supportFragmentManager.backStackEntryCount == 1) {
            showExitConfirmationDialog()
        } else{
            super.onBackPressed()
        }
    }


    private fun showExitConfirmationDialog() {
            val dialog = AlertDialog.Builder(ContextThemeWrapper(this, R.style.MyDialog))
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes") { _, _ ->
                    finish() // Close the activity
                }
                .setNegativeButton("No",){ dialog, which  ->
                    //dialog.dismiss()
                }
                .setNeutralButton("Not sure") { _, _ ->
                    Toast.makeText(this, "I need to think about it", Toast.LENGTH_SHORT).show()
                }
                .setView(EditText(this))
                .create()
            dialog.show()
    }


}