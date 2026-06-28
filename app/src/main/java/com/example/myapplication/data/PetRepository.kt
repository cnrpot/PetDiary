package com.example.myapplication.data

import kotlinx.coroutines.flow.Flow

/**
 * 宠物日记的数据仓库，统一管理所有数据源
 */
class PetRepository(
    private val petDao: PetDao,
    private val diaryDao: DiaryDao,
    private val reminderDao: ReminderDao,
    private val weightDao: WeightDao
) {
    val allPets: Flow<List<Pet>> = petDao.getAllPets()

    fun getPetById(id: Long): Flow<Pet?> = petDao.getPetById(id)

    val allDiaries: Flow<List<DiaryEntry>> = diaryDao.getAllDiaries()

    suspend fun insertPet(pet: Pet): Long = petDao.insertPet(pet)

    suspend fun updatePet(pet: Pet) = petDao.updatePet(pet)

    suspend fun updatePetWeight(id: Long, weight: Double) = petDao.updatePetWeight(id, weight)

    suspend fun deletePet(pet: Pet) = petDao.deletePet(pet)

    fun getDiaryEntriesForPet(petId: Long): Flow<List<DiaryEntry>> = diaryDao.getDiaryEntriesForPet(petId)

    suspend fun insertDiaryEntry(entry: DiaryEntry) = diaryDao.insertDiaryEntry(entry)

    suspend fun deleteDiaryEntry(entry: DiaryEntry) = diaryDao.deleteDiaryEntry(entry)

    fun getRemindersForPet(petId: Long): Flow<List<Reminder>> = reminderDao.getRemindersForPet(petId)

    fun getAllReminders(): Flow<List<Reminder>> = reminderDao.getAllReminders()

    suspend fun insertReminder(reminder: Reminder) = reminderDao.insertReminder(reminder)

    suspend fun updateReminder(reminder: Reminder) = reminderDao.updateReminder(reminder)

    suspend fun deleteReminder(reminder: Reminder) = reminderDao.deleteReminder(reminder)

    fun getWeightEntriesForPet(petId: Long): Flow<List<WeightEntry>> = weightDao.getWeightEntriesForPet(petId)

    suspend fun insertWeightEntry(entry: WeightEntry) = weightDao.insertWeightEntry(entry)
}
