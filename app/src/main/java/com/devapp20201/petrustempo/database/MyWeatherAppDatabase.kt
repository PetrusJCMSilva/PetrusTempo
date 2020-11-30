package com.devapp20201.petrustempo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devapp20201.petrustempo.dao.CityDataBaseDao
import com.devapp20201.petrustempo.model.CityDatabase

@Database(entities = arrayOf(CityDatabase::class), version = 1)

abstract class MyWeatherAppDatabase : RoomDatabase() {

    abstract fun cityDatabaseDao() : CityDataBaseDao

    companion object{
        var INSTANCE: MyWeatherAppDatabase? = null

        fun getInstance(context: Context): MyWeatherAppDatabase?{

            if(INSTANCE==null){
                synchronized(MyWeatherAppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MyWeatherAppDatabase::class.java,
                        "myweather.db").allowMainThreadQueries().build()
                }
            }

            return INSTANCE
        }
    }

}