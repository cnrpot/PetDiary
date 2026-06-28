package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * 应用的主数据库类，包含宠物、日记、提醒和体重表
 */
@Database(entities = [Pet::class, DiaryEntry::class, Reminder::class, WeightEntry::class], version = 2, exportSchema = false)
abstract class PetDiaryDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun diaryDao(): DiaryDao
    abstract fun reminderDao(): ReminderDao
    abstract fun weightDao(): WeightDao

    companion object {
        @Volatile
        private var INSTANCE: PetDiaryDatabase? = null

        /**
         * 获取数据库单例，确保线程安全
         */
        fun getDatabase(context: Context): PetDiaryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PetDiaryDatabase::class.java,
                    "pet_diary_database"
                )
                .fallbackToDestructiveMigration() // 模式更改时清除旧数据重新创建
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
