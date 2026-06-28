package com.example.myapplication.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * 提醒事项的数据访问对象 (DAO)
 */
@Dao
interface ReminderDao {
    /**
     * 获取指定宠物的提醒，按时间升序排列
     */
    @Query("SELECT * FROM reminders WHERE petId = :petId ORDER BY time ASC")
    fun getRemindersForPet(petId: Long): Flow<List<Reminder>>

    /**
     * 获取所有宠物的提醒
     */
    @Query("SELECT * FROM reminders ORDER BY time ASC")
    fun getAllReminders(): Flow<List<Reminder>>

    /**
     * 插入提醒
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    /**
     * 更新提醒（如标记完成）
     */
    @Update
    suspend fun updateReminder(reminder: Reminder)

    /**
     * 删除提醒
     */
    @Delete
    suspend fun deleteReminder(reminder: Reminder)
}
