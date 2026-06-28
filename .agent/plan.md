# Project Plan

宠物日记 (Pet Diary) - 一个记录宠物成长的安卓app。
主要功能包括：
- 宠物个人资料（名字、品种、生日、体重等）
- 日记记录（文字、照片、视频）
- 成长轨迹/里程碑
- 提醒功能（打疫苗、驱虫、喂食等）
- 体重记录与图表展示
- 界面美观，符合Material Design 3，充满活力。
- 适配各种设备屏幕。

## Project Brief

# 宠物日记 (Pet Diary) - Project Brief

宠物日记 (Pet Diary) is a vibrant and user-friendly Android application designed for pet owners to document their pets' growth journey, manage health schedules, and preserve precious memories.

## Features
1. **Pet Profiles**: Create and manage personalized profiles for multiple pets, tracking essential details like breed, birthday, and physical characteristics.
2. **Growth Diary**: Record daily moments and significant milestones using text and photos to build a chronological timeline of your pet's life.
3. **Health & Care Reminders**: Set up automated notifications for recurring tasks such as vaccinations, deworming, feeding, and medical check-ups.
4. **Weight Monitoring**: Log pet weight over time and visualize growth trends to ensure your pet stays healthy and fit.

## High-Level Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with **Material Design 3** for a modern, energetic aesthetic.
- **Navigation**: **Jetpack Navigation 3** (state-driven) for seamless screen transitions.
- **Adaptive Layouts**: **Compose Material Adaptive** library to ensure a premium experience across phones, foldables, and tablets.
- **Concurrency**: Kotlin Coroutines and Flow for responsive asynchronous operations.
- **Media Support**: **CameraX** for capturing diary photos and **Coil** for high-performance image loading.
- **Data Persistence**: **Room Database** for secure, local storage of pet records and diary entries.

## Implementation Steps

### Task_1_Core_and_Profiles: Set up the Room database, data models, and the UI for creating and managing pet profiles.
- **Status:** IN_PROGRESS
- **Acceptance Criteria:**
  - Room database and DAOs implemented for Pets, Diary, and Reminders
  - Pet profile creation and viewing UI functional
  - Material 3 basic layout applied
  - Project builds successfully
- **StartTime:** 2026-06-26 22:16:03 CST

### Task_2_Diary_and_Reminders: Implement the Growth Diary with photo support (CameraX/Coil) and the Care Reminder system.
- **Status:** PENDING
- **Acceptance Criteria:**
  - Diary entries can be created with text and images
  - Care reminders (vaccination, feeding) can be scheduled and triggered
  - Edge-to-Edge display enabled

### Task_3_Weight_Adaptive_and_Icon: Implement weight tracking with charts, apply energetic M3 theme, ensure adaptive layouts, and create the app icon.
- **Status:** PENDING
- **Acceptance Criteria:**
  - Weight tracking logs and charts functional
  - Adaptive layouts for phone and tablet using Compose Material Adaptive
  - Energetic M3 color scheme implemented
  - Adaptive app icon matching the pet theme created

### Task_4_Run_and_Verify: Final run and verification of the application.
- **Status:** PENDING
- **Acceptance Criteria:**
  - App is stable and crash-free
  - All features (Profiles, Diary, Reminders, Weight) work as intended
  - UI is vibrant and energetic
  - Build passes and all tests pass

