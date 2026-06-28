package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * 提醒事项实体类，用于健康和护理提醒
 */
@Entity(
    tableName = "reminders",
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
data class Reminder(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // 提醒 ID
    val petId: Long, // 关联的宠物 ID
    val title: String, // 提醒标题
    val description: String? = null, // 详细描述
    val time: Long, // 提醒时间
    val type: String, // 提醒类型（如：喂食、疫苗）
    val isCompleted: Boolean = false, // 是否已完成
    val repeatInterval: Long? = null // 重复间隔
)
