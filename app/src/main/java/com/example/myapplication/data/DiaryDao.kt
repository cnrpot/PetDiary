package com.example.myapplication.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * 日记表的数据访问对象 (DAO)
 */
@Dao
interface DiaryDao {
    /**
     * 获取指定宠物的日记，按时间倒序排列
     */
    @Query("SELECT * FROM diary_entries WHERE petId = :petId ORDER BY date DESC")
    fun getDiaryEntriesForPet(petId: Long): Flow<List<DiaryEntry>>

    /**
     * 获取所有宠物的日记记录，按时间倒序排列
     */
    @Query("SELECT * FROM diary_entries ORDER BY date DESC")
    fun getAllDiaries(): Flow<List<DiaryEntry>>

    /**
     * 插入日记
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiaryEntry(entry: DiaryEntry)

    /**
     * 删除日记
     */
    @Delete
    suspend fun deleteDiaryEntry(entry: DiaryEntry)
}
