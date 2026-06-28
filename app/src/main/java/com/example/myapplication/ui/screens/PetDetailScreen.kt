package com.example.myapplication.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.data.DiaryEntry
import com.example.myapplication.data.Pet
import com.example.myapplication.data.Reminder
import com.example.myapplication.data.WeightEntry
import com.example.myapplication.ui.components.DiaryCard
import com.example.myapplication.viewmodel.PetViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetDetailScreen(
    petId: Long,
    viewModel: PetViewModel,
    onBack: () -> Unit,
    onAddDiary: (Long) -> Unit,
    onAddReminder: (Long) -> Unit,
    onAddWeight: (Long) -> Unit
) {
    val pet by viewModel.getPetById(petId).collectAsState(initial = null)
    val diaryEntries by viewModel.getDiaryEntriesForPet(petId).collectAsState(initial = emptyList())
    val reminders by viewModel.getRemindersForPet(petId).collectAsState(initial = emptyList())
    val weightEntries by viewModel.getWeightEntriesForPet(petId).collectAsState(initial = emptyList())

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("日记", "提醒", "体重")
    
    var showDeleteDialog by remember { mutableStateOf(false) }

    // 图片选择器
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            pet?.let { currentPet ->
                viewModel.updatePet(currentPet.copy(photoUri = it.toString()))
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("确认删除") },
            text = { Text("确定要告别小可爱 ${pet?.name} 吗？相关的所有日记和记录也将被永久删除。") },
            confirmButton = {
                TextButton(
                    onClick = {
                        pet?.let { viewModel.deletePet(it) }
                        showDeleteDialog = false
                        onBack()
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                ) {
                    Text("确认删除")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("取消")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pet?.name ?: "宠物详情", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Rounded.Delete, contentDescription = "删除", tint = Color.Gray)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    when (selectedTab) {
                        0 -> onAddDiary(petId)
                        1 -> onAddReminder(petId)
                        2 -> onAddWeight(petId)
                    }
                },
                containerColor = Color(0xFFFF8A9E),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "添加")
            }
        }
    ) { padding ->
        pet?.let { currentPet ->
            Column(modifier = Modifier.padding(padding).background(Color(0xFFFDF7F7))) {
                // 头部个人资料
                PetDetailHeader(
                    pet = currentPet,
                    onPhotoClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )

                // 分段选择
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFFFF8A9E),
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = Color(0xFFFF8A9E)
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal) }
                        )
                    }
                }

                when (selectedTab) {
                    0 -> DiaryListDetail(diaryEntries, currentPet)
                    1 -> ReminderList(reminders, onToggleReminder = { viewModel.updateReminder(it) })
                    2 -> WeightHistoryList(weightEntries)
                }
            }
        }
    }
}

@Composable
fun PetDetailHeader(pet: Pet, onPhotoClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(3.dp, Color(0xFFFFC1CC), CircleShape)
                .clickable { onPhotoClick() },
            contentAlignment = Alignment.Center
        ) {
            if (pet.photoUri != null) {
                AsyncImage(
                    model = pet.photoUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    Icons.Rounded.Pets,
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    tint = Color(0xFFFFC1CC)
                )
                // 提示可以上传图片
                Icon(
                    Icons.Rounded.PhotoCamera,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.BottomEnd).padding(4.dp).size(24.dp),
                    tint = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(pet.name, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF4A4A4A))
        Text("${pet.species} · ${pet.breed} · ${pet.gender}", color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFFFE4E8)
        ) {
            Text(
                "${pet.weight} kg",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                color = Color(0xFFFF8A9E),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DiaryListDetail(entries: List<DiaryEntry>, pet: Pet) {
    if (entries.isEmpty()) {
        EmptyStateDetail("还没有日记记录哦", Icons.Rounded.History)
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(entries) { entry ->
                DiaryCard(entry, pet)
            }
        }
    }
}

@Composable
fun ReminderList(reminders: List<Reminder>, onToggleReminder: (Reminder) -> Unit) {
    if (reminders.isEmpty()) {
        EmptyStateDetail("还没有提醒事项哦", Icons.Rounded.Event)
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(reminders) { reminder ->
                ReminderItem(reminder, onToggleReminder)
            }
        }
    }
}

@Composable
fun ReminderItem(reminder: Reminder, onToggleReminder: (Reminder) -> Unit) {
    val timeFormat = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = if (reminder.isCompleted) 
            CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)) 
            else CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = reminder.isCompleted,
                onCheckedChange = { onToggleReminder(reminder.copy(isCompleted = it)) }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    reminder.title,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (reminder.isCompleted) androidx.compose.ui.text.style.TextDecoration.LineThrough else null
                )
                Text(
                    "${reminder.type} · ${timeFormat.format(Date(reminder.time))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun WeightHistoryList(entries: List<WeightEntry>) {
    if (entries.isEmpty()) {
        EmptyStateDetail("还没有体重记录哦", Icons.Rounded.MonitorWeight)
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(entries) { entry ->
                WeightItem(entry)
            }
        }
    }
}

@Composable
fun WeightItem(entry: WeightEntry) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(dateFormat.format(Date(entry.date)), style = MaterialTheme.typography.bodyLarge)
            Text("${entry.weight} kg", style = MaterialTheme.typography.headlineSmall, color = Color(0xFFFF8A9E), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun EmptyStateDetail(message: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.LightGray)
            Spacer(modifier = Modifier.height(16.dp))
            Text(message, color = Color.Gray)
        }
    }
}
