package com.devapp20201.petrustempo

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devapp20201.petrustempo.R.layout.recyclerview_item
import com.devapp20201.petrustempo.model.Element
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class MyAdapter (val list: MutableList<Element>?): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
    fun addItems(newElements: MutableList<Element>?){
        list?.clear()
        newElements?.forEach {list?.add(it)}
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false))
    }

    override fun onBindViewHolder (holder: MyViewHolder, position: Int){
        when(holder){
            is MyViewHolder->{
                if (position <(list?.size?: 0)){
                    val element = list?.get(position)
                    if (element != null){
                        holder.bindView(element)
                    }
                }
            }
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val tvCityName: TextView = itemView.tv_city_name
        private val tvCityCode: TextView = itemView.tv_city_code
        private val ivWeather: ImageView = itemView.iv_city_weather

        fun bindView(element: Element) = with(itemView){
            tvCityName.text = element.name
            tvCityCode.text = element.id.toString()

            Glide.with(context)
                    .load("http://openweathermap.org/img/wn/${element.weather[0].icon}@4x.png")
                    .placeholder(R.drawable.ic_weather_placeholder)
                    .error(R.drawable.ic_weather_placeholder)
                    .circleCrop()
                    .into(ivWeather)
        }

    }
    class MyItemDecoration(private val height: Int): RecyclerView.ItemDecoration(){

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            with(outRect){
                if (parent.getChildAdapterPosition(view) == 0){
                    top = height
                }
                left = height
                right = height
                bottom = height
            }
        }

    }



}