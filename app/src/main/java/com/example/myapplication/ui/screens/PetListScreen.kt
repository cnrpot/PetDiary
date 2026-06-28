package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.data.Pet
import com.example.myapplication.ui.components.DiaryCard
import com.example.myapplication.viewmodel.PetViewModel

@Composable
fun PetListScreen(
    viewModel: PetViewModel,
    onAddPetClick: () -> Unit,
    onPetClick: (Long) -> Unit,
    onAddDiaryClick: () -> Unit
) {
    val pets by viewModel.allPets.collectAsState()
    val allDiaries by viewModel.allDiaries.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFFDF7F7))) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 1. 顶部 Header，包含宠物列表
            DiaryHeader(pets, onAddPetClick, onPetClick)

            // 2. 日记列表
            if (allDiaries.isEmpty()) {
                EmptyDiaryState(pets.isNotEmpty())
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(allDiaries) { entry ->
                        val pet = pets.find { it.id == entry.petId }
                        DiaryCard(entry, pet)
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }

        // 3. 悬浮按钮
        if (pets.isNotEmpty()) {
            ExtendedFloatingActionButton(
                onClick = onAddDiaryClick,
                containerColor = Color(0xFFFF8A9E),
                contentColor = Color.White,
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .height(56.dp)
                    .width(160.dp),
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("新建日记", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun DiaryHeader(pets: List<Pet>, onAddPetClick: () -> Unit, onPetClick: (Long) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFDDE1), Color(0xFFFDF7F7))
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "宠物日记",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFFFF8A9E),
                letterSpacing = 4.sp
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            // 横向宠物列表
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(pets) { pet ->
                    PetAvatarItem(pet = pet, onClick = { onPetClick(pet.id) })
                }
                item {
                    AddPetCircle(onClick = onAddPetClick)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (pets.isEmpty()) "点击右侧按钮添加宠物" else "我的小可爱们",
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun PetAvatarItem(pet: Pet, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(2.dp, Color(0xFFFF8A9E), CircleShape)
                .clickable { onClick() },
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
                    modifier = Modifier.size(44.dp),
                    tint = if (pet.gender == "母") Color(0xFFFFC1CC) else Color(0xFF81D4FA)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(pet.name, fontSize = 12.sp, color = Color(0xFF4A4A4A), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun AddPetCircle(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.5f))
            .border(1.dp, Color.LightGray, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Default.Add, contentDescription = null, tint = Color.Gray)
    }
}

@Composable
fun EmptyDiaryState(hasPets: Boolean) {
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 80.dp, start = 40.dp, end = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Rounded.Pets,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = Color(0xFFEEEEEE)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (hasPets) "宠物已就位！\n快点击下方按钮记录它的第一篇日记吧~" else "还没有宠物哦，\n先点击上方的加号添加一个小可爱吧！",
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )
    }
}
