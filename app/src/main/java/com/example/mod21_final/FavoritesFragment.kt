package com.example.mod21_final

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/*

 */
class FavoritesFragment : Fragment() , OnItemClickListener {

    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favorites_recycler= view.findViewById<RecyclerView>(R.id.favorites_recycler)
        filmsAdapter = FilmListRecyclerAdapter(this)
        favorites_recycler
            .apply {
                adapter = filmsAdapter                                           //Присваиваем адаптер
                layoutManager = LinearLayoutManager(requireContext())           //Присвои layoutmanager
                val decorator = TopSpacingItemDecoration(8)         //Применяем декоратор для отступов
                addItemDecoration(decorator)
            }
        filmsAdapter.addItems((requireActivity() as MainActivity).filmsDataBase)    //Кладем нашу БД в RV

    }

    override fun onItemClick(position: Int) {
        val activity = requireActivity() as MainActivity
        val film = activity.filmsDataBase[position]
        activity.launchDetailsFragment(film)
    }

}