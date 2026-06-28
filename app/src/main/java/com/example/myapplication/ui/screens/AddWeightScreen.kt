package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.WeightEntry
import com.example.myapplication.viewmodel.PetViewModel

/**
 * 添加体重记录页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWeightScreen(
    petId: Long,
    viewModel: PetViewModel,
    onBack: () -> Unit
) {
    var weight by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("记录体重") },
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
                value = weight,
                onValueChange = { weight = it },
                label = { Text("当前体重 (kg)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val w = weight.toDoubleOrNull()
                    if (w != null) {
                        viewModel.insertWeightEntry(
                            WeightEntry(
                                petId = petId,
                                weight = w,
                                date = System.currentTimeMillis()
                            )
                        )
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = weight.toDoubleOrNull() != null
            ) {
                Text("确认记录")
            }
        }
    }
}
