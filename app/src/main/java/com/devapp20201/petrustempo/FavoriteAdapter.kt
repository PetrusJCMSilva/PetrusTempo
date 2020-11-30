package com.devapp20201.petrustempo

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devapp20201.petrustempo.model.CityDatabase
import kotlinx.android.synthetic.main.favorites_items.view.*

class FavoriteAdapter(val list: List<CityDatabase>?)
    : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
       return FavoriteViewHolder(LayoutInflater.from(parent.context)
               .inflate(R.layout.favorites_items, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        when(holder){
            is FavoriteAdapter.FavoriteViewHolder ->{
                if (position <(list!!.size)){
                    val element = list.get(position)
                    if (element != null){
                        holder.bindView(element)
                    }
                }
            }
        }
    }


    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvCityName = itemView.tv_favorite_city_name
        val tvCityCode = itemView.tv_favorite_city_code

        fun bindView(cityDatabase: CityDatabase){
            tvCityName.text = cityDatabase.cityName
            tvCityCode.text = cityDatabase.id.toString()
        }

    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    class FavoriteItemDecoration(val size: Int) : RecyclerView.ItemDecoration(){

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            with(outRect){
                if (parent.getChildAdapterPosition(view) == 0){
                    top = size
                }
                left = size
                right = size
                bottom = size
            }
        }

    }
}