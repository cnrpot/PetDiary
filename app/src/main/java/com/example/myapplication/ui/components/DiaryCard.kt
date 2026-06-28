package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.DiaryEntry
import com.example.myapplication.data.Pet
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DiaryCard(entry: DiaryEntry, pet: Pet?) {
    val borderColor = if (pet?.gender == "母") Color(0xFFFFC1CC) else Color(0xFF81D4FA)
    val petIconBg = if (pet?.gender == "母") Color(0xFFFFE4E8) else Color(0xFFE1F5FE)

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, borderColor, RoundedCornerShape(24.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(petIconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Rounded.Pets,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = borderColor
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                val sdf = SimpleDateFormat("MM月dd日 EEEE", Locale.CHINA)
                Text(
                    text = sdf.format(Date(entry.date)),
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f)
                )
                
                Text(text = entry.weather ?: "☀️", modifier = Modifier.padding(horizontal = 4.dp))
                Text(text = entry.mood ?: "😊", modifier = Modifier.padding(horizontal = 4.dp))
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = entry.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A4A4A)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = entry.content,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2
            )

            if (entry.photoUri != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF5F5F5)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Rounded.Pets, contentDescription = null, tint = Color.LightGray)
                }
            }
        }
    }
}
