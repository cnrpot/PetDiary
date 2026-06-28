package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.DiaryEntry
import com.example.myapplication.viewmodel.PetViewModel

/**
 * 添加日记页面 - 适配新设计
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDiaryScreen(
    petId: Long,
    viewModel: PetViewModel,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var mood by remember { mutableStateOf("😊") }
    var weather by remember { mutableStateOf("☀️") }

    val moods = listOf("😊", "😂", "😍", "😴", "😢")
    val weathers = listOf("☀️", "☁️", "🌧️", "❄️", "🌬️")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("记录此刻") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("标题 (如：今天学会了坐下！)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("记录宠物的生活点滴...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                minLines = 5
            )

            Text("心情", style = MaterialTheme.typography.labelLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                moods.forEach { m ->
                    FilterChip(
                        selected = mood == m,
                        onClick = { mood = m },
                        label = { Text(m) }
                    )
                }
            }

            Text("天气", style = MaterialTheme.typography.labelLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                weathers.forEach { w ->
                    FilterChip(
                        selected = weather == w,
                        onClick = { weather = w },
                        label = { Text(w) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (content.isNotBlank() && title.isNotBlank()) {
                        viewModel.insertDiaryEntry(
                            DiaryEntry(
                                petId = petId,
                                title = title,
                                content = content,
                                mood = mood,
                                weather = weather,
                                date = System.currentTimeMillis()
                            )
                        )
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = content.isNotBlank() && title.isNotBlank()
            ) {
                Text("保存日记")
            }
        }
    }
}
