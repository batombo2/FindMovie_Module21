package com.example.mod21_final

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class HomeFragment : Fragment(), OnItemClickListener{ // OnItemClickListener

    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var filmsDataBase: List<Film>

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
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
        /*
        mainRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 1) {
                    searchView.visibility = View.GONE
                } else if( dy < -1 ) {
                    searchView.visibility = View.VISIBLE
                }
            }
        })
         */

        return view

    }

    override fun onItemClick(position: Int) {
        val activity = requireActivity() as MainActivity
        val film = activity.filmsDataBase[position]
        activity.launchDetailsFragment(film)
    }
}