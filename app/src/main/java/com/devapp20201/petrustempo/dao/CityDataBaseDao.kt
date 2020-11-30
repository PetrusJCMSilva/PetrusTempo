package com.devapp20201.petrustempo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devapp20201.petrustempo.model.CityDatabase

@Dao
interface CityDataBaseDao{
    @Insert(onConflict  = OnConflictStrategy.REPLACE)
    fun save(cityDatabase: CityDatabase)

    @Query("SELECT * FROM citydatabase")
    fun getAllCityDatabase(): List<CityDatabase>

}