package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * 日记条目实体类，记录宠物成长点滴
 * 与 Pet 表通过 petId 关联，开启级联删除
 */
@Entity(
    tableName = "diary_entries",
    foreignKeys = [
        ForeignKey(
            entity = Pet::class,
            parentColumns = ["id"],
            childColumns = ["petId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("petId")]
)
data class DiaryEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // 日记 ID
    val petId: Long, // 关联的宠物 ID
    val date: Long, // 记录日期（时间戳）
    val title: String, // 日记标题
    val content: String, // 日记内容
    val mood: String? = "😊", // 心情图标
    val weather: String? = "☀️", // 天气图标
    val photoUri: String? = null // 可选的照片
)
