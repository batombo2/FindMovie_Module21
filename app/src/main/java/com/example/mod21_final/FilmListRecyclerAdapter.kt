package com.example.mod21_final

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class FilmListRecyclerAdapter (val clickListener: OnItemClickListener) : RecyclerView.Adapter<FilmListRecyclerAdapter .FilmViewHolder>() {

    private val items = mutableListOf<Film>()                     //Здесь у нас хранится список элементов для RV
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    inner class FilmViewHolder(val itemView: View ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title: TextView = itemView.findViewById(R.id.title)
        val poster : ImageView = itemView.findViewById(R.id.poster)
        val description: TextView = itemView.findViewById(R.id.description)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                clickListener.onItemClick(position)
            }
        }

        fun bind(film: Film) {
            title.text = film.title
            poster.setImageResource(film.poster)
            description.text = film.description
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val viewItem: View = inflater.inflate(R.layout.film_item, parent, false)
        return FilmViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return  items.size
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(items[position])


        //holder.itemView.setOnClickListener(){
        //    holder.title.text = "111111111"
        //}



        //val holderView = holder.itemView.findViewById<CardView>(R.id.item_container)

        //holderView.setOnClickListener {
        //    holder.title.text = "111111111"

            //clickListener.click(items[position])
        //}
    }

    //Метод для добавления объектов в наш список
    fun addItems(newList: List<Film>) {

        val oldList = items.toList()
        items.apply{
            clear()
            addAll(newList)
        }

        val diffCallback = FilmDiffCallback(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)

        // notifyDataSetChanged()
    }

    //Интерфейс для обработки кликов
    //interface OnItemClickListener {
    //    fun click(film: Film )
    //}
}