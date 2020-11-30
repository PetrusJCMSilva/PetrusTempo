package com.devapp20201.petrustempo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.devapp20201.petrustempo.FavoriteAdapter
import com.devapp20201.petrustempo.R
import com.devapp20201.petrustempo.database.MyWeatherAppDatabase

import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_search.*

class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val favoritesContainer = inflater.inflate(R.layout.fragment_favorites, container, false)

        return favoritesContainer
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = context?.let {MyWeatherAppDatabase.getInstance(it)}
        val list = db?.cityDatabaseDao()?.getAllCityDatabase()

        favorite_recyclerview.adapter = FavoriteAdapter(list)
        favorite_recyclerview.layoutManager = LinearLayoutManager(context)
        favorite_recyclerview.addItemDecoration(FavoriteAdapter.FavoriteItemDecoration(20))
    }


}
