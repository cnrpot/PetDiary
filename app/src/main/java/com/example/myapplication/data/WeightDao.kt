package com.example.myapplication.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * 体重表的数据访问对象 (DAO)
 */
@Dao
interface WeightDao {
    @Query("SELECT * FROM weight_entries WHERE petId = :petId ORDER BY date DESC")
    fun getWeightEntriesForPet(petId: Long): Flow<List<WeightEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeightEntry(entry: WeightEntry)

    @Delete
    suspend fun deleteWeightEntry(entry: WeightEntry)
}
