package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 宠物实体类，存储宠物的基本信息
 */
@Entity(tableName = "pets")
data class Pet(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // 主键 ID，自动生成
    val name: String, // 宠物名字
    val breed: String, // 品种
    val birthday: Long, // 出生日期（毫秒时间戳）
    val weight: Double, // 体重（kg）
    val gender: String, // 性别
    val species: String, // 物种（如：猫、狗）
    val photoUri: String? = null // 照片路径
)
