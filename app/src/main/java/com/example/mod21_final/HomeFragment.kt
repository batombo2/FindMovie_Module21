package com.example.mod21_final

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(), OnItemClickListener{ // OnItemClickListener

    private lateinit var filmsAdapter: FilmListRecyclerAdapter

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        filmsAdapter = FilmListRecyclerAdapter(this)
        val decorator = TopSpacingItemDecoration(8)
        val mainRecycler: RecyclerView = view.findViewById(R.id.main_recycler)
        mainRecycler.apply{
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager((requireActivity() as MainActivity))
            addItemDecoration(decorator)
        }
        filmsAdapter.addItems((requireActivity() as MainActivity).filmsDataBase)

        return view

    }

    override fun onItemClick(position: Int) {
        val activity = requireActivity() as MainActivity
        val film = activity.filmsDataBase[position]
        activity.launchDetailsFragment(film)
    }
}