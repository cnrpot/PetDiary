package com.example.myapplication.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * 宠物表的数据访问对象 (DAO)
 */
@Dao
interface PetDao {
    /**
     * 获取所有宠物，返回 Flow 以便 UI 实时监听
     */
    @Query("SELECT * FROM pets")
    fun getAllPets(): Flow<List<Pet>>

    /**
     * 根据 ID 获取单个宠物详情
     */
    @Query("SELECT * FROM pets WHERE id = :id")
    fun getPetById(id: Long): Flow<Pet?>

    /**
     * 插入新宠物，返回插入的行 ID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: Pet): Long

    /**
     * 更新宠物信息
     */
    @Update
    suspend fun updatePet(pet: Pet)

    /**
     * 仅更新宠物体重
     */
    @Query("UPDATE pets SET weight = :weight WHERE id = :id")
    suspend fun updatePetWeight(id: Long, weight: Double)

    /**
     * 删除宠物
     */
    @Delete
    suspend fun deletePet(pet: Pet)
}
