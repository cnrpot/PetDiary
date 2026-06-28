package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.myapplication.data.PetDiaryDatabase
import com.example.myapplication.data.PetRepository
import com.example.myapplication.ui.screens.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.PetViewModel
import com.example.myapplication.viewmodel.PetViewModelFactory
import kotlinx.serialization.Serializable

/**
 * 导航路由定义
 */
@Serializable
sealed class NavRoute : NavKey {
    @Serializable
    data object PetList : NavRoute()
    @Serializable
    data object AddPet : NavRoute()
    @Serializable
    data class PetDetail(val id: Long) : NavRoute()
    @Serializable
    data class AddDiary(val petId: Long) : NavRoute()
    @Serializable
    data class AddReminder(val petId: Long) : NavRoute()
    @Serializable
    data class AddWeight(val petId: Long) : NavRoute()
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()

        val database = PetDiaryDatabase.getDatabase(this)
        val repository = PetRepository(
            database.petDao(), 
            database.diaryDao(), 
            database.reminderDao(),
            database.weightDao()
        )

        setContent {
            MyApplicationTheme {
                val petViewModel: PetViewModel = viewModel(
                    factory = PetViewModelFactory(repository)
                )

                // Navigation 3 管理路由堆栈
                val backStack = rememberNavBackStack(NavRoute.PetList)

                // 适配性布局配置
                val windowAdaptiveInfo = currentWindowAdaptiveInfo()
                val directive = remember(windowAdaptiveInfo) {
                    calculatePaneScaffoldDirective(windowAdaptiveInfo)
                        .copy(horizontalPartitionSpacerSize = 0.dp)
                }
                val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>(directive = directive)

                NavDisplay(
                    backStack = backStack,
                    onBack = { if (backStack.size > 1) backStack.removeAt(backStack.size - 1) },
                    sceneStrategy = listDetailStrategy,
                    entryProvider = entryProvider {
                        // 宠物列表 - 列表面板
                        entry<NavRoute.PetList>(
                            metadata = ListDetailSceneStrategy.listPane(
                                detailPlaceholder = {
                                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                        Text("请从左侧选择一个宠物查看详情", style = MaterialTheme.typography.bodyLarge)
                                    }
                                }
                            )
                        ) {
                            PetListScreen(
                                viewModel = petViewModel,
                                onAddPetClick = { backStack.add(NavRoute.AddPet) },
                                onPetClick = { id -> backStack.add(NavRoute.PetDetail(id)) },
                                onAddDiaryClick = {
                                    val firstPetId = petViewModel.allPets.value.firstOrNull()?.id
                                    if (firstPetId != null) {
                                        backStack.add(NavRoute.AddDiary(firstPetId))
                                    } else {
                                        backStack.add(NavRoute.AddPet)
                                    }
                                }
                            )
                        }

                        // 宠物详情 - 详情面板
                        entry<NavRoute.PetDetail>(
                            metadata = ListDetailSceneStrategy.detailPane()
                        ) { route ->
                            PetDetailScreen(
                                petId = route.id,
                                viewModel = petViewModel,
                                onBack = { backStack.removeAt(backStack.size - 1) },
                                onAddDiary = { id -> backStack.add(NavRoute.AddDiary(id)) },
                                onAddReminder = { id -> backStack.add(NavRoute.AddReminder(id)) },
                                onAddWeight = { id -> backStack.add(NavRoute.AddWeight(id)) }
                            )
                        }

                        // 其他功能页面
                        entry<NavRoute.AddPet> {
                            AddPetScreen(
                                viewModel = petViewModel,
                                onBack = { backStack.removeAt(backStack.size - 1) }
                            )
                        }

                        entry<NavRoute.AddDiary> { route ->
                            AddDiaryScreen(
                                petId = route.petId,
                                viewModel = petViewModel,
                                onBack = { backStack.removeAt(backStack.size - 1) }
                            )
                        }

                        entry<NavRoute.AddReminder> { route ->
                            AddReminderScreen(
                                petId = route.petId,
                                viewModel = petViewModel,
                                onBack = { backStack.removeAt(backStack.size - 1) }
                            )
                        }

                        entry<NavRoute.AddWeight> { route ->
                            AddWeightScreen(
                                petId = route.petId,
                                viewModel = petViewModel,
                                onBack = { backStack.removeAt(backStack.size - 1) }
                            )
                        }
                    }
                )
            }
        }
    }
}
