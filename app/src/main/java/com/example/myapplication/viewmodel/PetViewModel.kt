package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * 宠物日记的视图模型，负责处理 UI 逻辑和数据交互
 */
class PetViewModel(private val repository: PetRepository) : ViewModel() {

    val allPets: StateFlow<List<Pet>> = repository.allPets
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * 所有日记记录流
     */
    val allDiaries: StateFlow<List<DiaryEntry>> = repository.allDiaries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertPet(pet: Pet) {
        viewModelScope.launch {
            repository.insertPet(pet)
        }
    }

    fun updatePet(pet: Pet) {
        viewModelScope.launch {
            repository.updatePet(pet)
        }
    }

    fun deletePet(pet: Pet) {
        viewModelScope.launch {
            repository.deletePet(pet)
        }
    }

    fun getPetById(id: Long): Flow<Pet?> = repository.getPetById(id)

    fun getDiaryEntriesForPet(petId: Long): Flow<List<DiaryEntry>> = 
        repository.getDiaryEntriesForPet(petId)

    fun insertDiaryEntry(entry: DiaryEntry) {
        viewModelScope.launch {
            repository.insertDiaryEntry(entry)
        }
    }

    fun getRemindersForPet(petId: Long): Flow<List<Reminder>> = 
        repository.getRemindersForPet(petId)

    fun insertReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.insertReminder(reminder)
        }
    }

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.updateReminder(reminder)
        }
    }

    fun getWeightEntriesForPet(petId: Long): Flow<List<WeightEntry>> =
        repository.getWeightEntriesForPet(petId)

    fun insertWeightEntry(entry: WeightEntry) {
        viewModelScope.launch {
            repository.insertWeightEntry(entry)
            // 同时更新宠物主表中的最新体重
            repository.updatePetWeight(entry.petId, entry.weight)
        }
    }
}
