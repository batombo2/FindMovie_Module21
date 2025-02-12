package com.example.mod21_final

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Scene
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import java.util.Locale

class HomeFragment : Fragment(), OnItemClickListener{ // OnItemClickListener

    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var filmsDataBase: List<Film>

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }


    //  This method is called after onCreateView, once the view hierarchy has been created and the fragment's view is fully inflated.
    //  It is used to initialize views (e.g., setting up RecyclerView adapters, adding listeners, etc.)
    //  and perform any logic that requires the view to be fully created.
    //        Use onViewCreated for initializing views and setting up logic after the view has been created.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val  homeFragmentRoot : ConstraintLayout = view.findViewById(R.id.home_fragment_root)
        val scene = Scene.getSceneForLayout(homeFragmentRoot, R.layout.merge_home_screen_content, requireContext())

        val searchSlide = Slide(Gravity.START).addTarget(R.id.search_view)  //  .TOP
        val recyclerSlide = Slide(Gravity.END).addTarget(R.id.main_recycler)  // .BOTTOM

        val customTransition = TransitionSet().apply {
            duration = 500
            addTransition(recyclerSlide)
            addTransition(searchSlide)
        }

        TransitionManager.go(scene , customTransition)
        //TransitionManager.go(scene)


        val searchView : SearchView = view.findViewById<SearchView>(R.id.search_view)
        val mainRecycler: RecyclerView = view.findViewById(R.id.main_recycler)

        filmsDataBase = (requireActivity() as MainActivity).filmsDataBase
        filmsAdapter = FilmListRecyclerAdapter(this)
        val decorator = TopSpacingItemDecoration(8)

        mainRecycler.apply{
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager((requireActivity() as MainActivity))
            addItemDecoration(decorator)
        }
        filmsAdapter.addItems(filmsDataBase)


        searchView.setOnClickListener(){                                    // пальцем в лупу
            searchView.isIconified = false
        }

        val queryTextListener = object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {        // нажатии кнопки "поиск" на софт клавиатуре
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {         //на каждое изменения текста
                if (newText == null) return false
                if (newText.isEmpty()) {                                     //Если ввод пуст то вставляем в адаптер всю БД
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }
                val result = filmsDataBase.filter {                             //Фильтруем список на поискк подходящих сочетаний
                    it.title.toLowerCase(Locale.getDefault())
                        .contains(newText.toLowerCase(Locale.getDefault()))     // запрос, и имя фильма приводить к нижнему регистру
                }
                filmsAdapter.addItems(result)                                   //Добавляем в адаптер
                return true
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)

        //*******************************************************
        //   схлапываем SearchView
        //
    //        mainRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (dy > 1) {
//                    searchView.visibility = View.GONE
//                } else if( dy < -1 ) {
//                    searchView.visibility = View.VISIBLE
//                }
//            }
//        })
    }






    // This method is called first (before onViewCreated) when the fragment's view hierarchy is being created.
    // It is responsible for inflating the layout of the fragment.
    // You typically return the root view of the fragment's layout from this method.
    //       Use onCreateView for inflating the layout.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view


    }








    override fun onItemClick(position: Int) {
        val activity = requireActivity() as MainActivity
        val film = activity.filmsDataBase[position]
        activity.launchDetailsFragment(film)
    }
}