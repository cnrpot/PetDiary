package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.Reminder
import com.example.myapplication.viewmodel.PetViewModel
import java.util.*

/**
 * 添加提醒页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderScreen(
    petId: Long,
    viewModel: PetViewModel,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("喂食") }
    val types = listOf("喂食", "洗澡", "驱虫", "疫苗", "其他")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("新建提醒") },
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
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("提醒事项内容") },
                modifier = Modifier.fillMaxWidth()
            )

            Text("提醒类型", style = MaterialTheme.typography.labelLarge)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                types.forEach { t ->
                    FilterChip(
                        selected = type == t,
                        onClick = { type = t },
                        label = { Text(t) }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        viewModel.insertReminder(
                            Reminder(
                                petId = petId,
                                title = title,
                                type = type,
                                time = System.currentTimeMillis() // 简化版，默认当前时间，实际应提供时间选择器
                            )
                        )
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank()
            ) {
                Text("开启提醒")
            }
        }
    }
}
