package com.devapp20201.petrustempo.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devapp20201.petrustempo.MyAdapter
import com.devapp20201.petrustempo.R
import com.devapp20201.petrustempo.database.MyWeatherAppDatabase
import com.devapp20201.petrustempo.manager.OpenWeatherManager
import com.devapp20201.petrustempo.model.City
import com.devapp20201.petrustempo.model.CityDatabase
import com.devapp20201.petrustempo.model.Element
import com.devapp20201.petrustempo.model.Root

import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment(), View.OnClickListener {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSearch.setOnClickListener(this)
        floatingActionButton.setOnClickListener{
            val city = et_city.text.toString()

            val service = OpenWeatherManager().getOpenWeatherService()

            val call = service.getCityWeather(city)

            call.enqueue(object : Callback<City>{

                override fun onResponse(call: Call<City>, response: Response<City>) {
                    when(response.isSuccessful){
                        true->{

                            val city = response.body()

                            if(context!= null){
                                val db = MyWeatherAppDatabase.getInstance(context!!)

                               val cityDatabase = CityDatabase(city!!.id, city!!.name)

                                val result = db?.cityDatabaseDao()?.save(cityDatabase)

                                Toast.makeText(context, "Result : $result", Toast.LENGTH_LONG)
                            }



                        }false->{

                             Log.e("HSS", "Response failure")
                        }
                    }
                }

                override fun onFailure(call: Call<City>, t: Throwable) {
                    Log.e("HSS", "There is a error: ${t.message}")
                }

            })
        }
        recyclerView.adapter = MyAdapter(mutableListOf())

    }

    fun isConnectivityAvailable(context: Context): Boolean{
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            cm.getNetworkCapabilities(cm.activeNetwork)?.run{
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }else{
            cm.activeNetworkInfo.run{
                if(type == ConnectivityManager.TYPE_WIFI){
                    result = true
                }else if(type == ConnectivityManager.TYPE_MOBILE){
                    result = true
                }
            }
        }
        return result
    }

    override fun onClick(view: View?) {
        when (view?.context?.let{ isConnectivityAvailable(it)}){
            true-> {
               progressBar.visibility = View.VISIBLE

                Toast.makeText(view?.context, "Online", Toast.LENGTH_LONG).show()

                val city = et_city.text.toString()
                Log.d("HSS", "Searching city $city")
                val service = OpenWeatherManager().getOpenWeatherService()

                val call = service.findTemperature(city)
                call.enqueue(object : Callback<Root> {
                    override fun onResponse(call: Call<Root>, response: Response<Root>) {
                        when(response.isSuccessful){
                            true->{
                                val root = response.body()
                                Log.d("HSS", "Returned root element: $root")
                                progressBar.visibility = View.GONE

                                val elements = mutableListOf<Element>()
                                root?.list?.forEach{
                                        elements.add(it)
                                }
                                (recyclerView.adapter as MyAdapter).addItems(elements)
                                recyclerView.layoutManager = LinearLayoutManager(context)
                                recyclerView.addItemDecoration(MyAdapter.MyItemDecoration(30))


                            }false-> {
                            Log.e("HSS", "Response failure")
                            }
                        }
                    }

                    override fun onFailure(call: Call<Root>, t: Throwable) {
                        Log.e("HSS", "There is a error: ${t.message}")
                    }
                })
            }
            false-> {
                Toast.makeText(view?.context,"Offline", Toast.LENGTH_LONG).show()
            }
        }


    }


}