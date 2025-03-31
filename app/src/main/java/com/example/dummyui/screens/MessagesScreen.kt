package com.example.dummyui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dummyui.navigation.AppScreens
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

data class ChatMessage(
    val id: String,
    val senderName: String,
    val message: String,
    val timestamp: Date,
    val unreadCount: Int = 0,
    val assignmentTitle: String,
    val isActive: Boolean = true
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(navController: NavController) {
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Active", "Completed")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Messages") },
                actions = {
                    IconButton(onClick = { /* Search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = { navController.navigate(AppScreens.HomeScreen.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Assignment, contentDescription = "Assignments") },
                    label = { Text("Assignments") },
                    selected = false,
                    onClick = { navController.navigate(AppScreens.AssignmentsScreen.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Email, contentDescription = "Messages") },
                    label = { Text("Messages") },
                    selected = true,
                    onClick = { /* Already on messages */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = false,
                    onClick = { navController.navigate(AppScreens.ProfileScreen.route) }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Filter chips
            ScrollableFilterChips(
                filters = filters,
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it }
            )

            // Messages list
            ChatList(
                chats = generateSampleChats(),
                onChatSelected = {
                }
            )
        }
    }
}

@Composable
fun ScrollableFilterChips(
    filters: List<String>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                label = { Text(filter) },
                modifier = Modifier.height(32.dp)
            )
        }
    }
}

@Composable
fun ChatList(
    chats: List<ChatMessage>,
    onChatSelected: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(chats) { chat ->
            ChatListItem(
                chat = chat,
                onClick = { onChatSelected(chat.id) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListItem(
    chat: ChatMessage,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = chat.senderName.first().toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Message content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = chat.senderName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = formatChatTime(chat.timestamp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Re: ${chat.assignmentTitle}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = chat.message,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = if (chat.unreadCount > 0)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (chat.unreadCount > 0) {
                Spacer(modifier = Modifier.width(8.dp))

                // Unread indicator
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = chat.unreadCount.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

private fun formatChatTime(date: Date): String {
    val now = Calendar.getInstance()
    val messageTime = Calendar.getInstance().apply { time = date }

    return when {
        now.get(Calendar.DAY_OF_YEAR) == messageTime.get(Calendar.DAY_OF_YEAR) &&
                now.get(Calendar.YEAR) == messageTime.get(Calendar.YEAR) -> {
            // Today - show time only
            SimpleDateFormat("h:mm a", Locale.getDefault()).format(date)
        }
        now.get(Calendar.DAY_OF_YEAR) - messageTime.get(Calendar.DAY_OF_YEAR) == 1 &&
                now.get(Calendar.YEAR) == messageTime.get(Calendar.YEAR) -> {
            // Yesterday
            "Yesterday"
        }
        now.get(Calendar.WEEK_OF_YEAR) == messageTime.get(Calendar.WEEK_OF_YEAR) &&
                now.get(Calendar.YEAR) == messageTime.get(Calendar.YEAR) -> {
            // This week - show day name
            SimpleDateFormat("EEE", Locale.getDefault()).format(date)
        }
        else -> {
            // Older - show date
            SimpleDateFormat("MMM d", Locale.getDefault()).format(date)
        }
    }
}

private fun generateSampleChats(): List<ChatMessage> {
    val names = listOf("Alice Johnson", "Bob Smith", "Charlie Brown", "Diana Prince", "Edward Norton")
    val assignments = listOf(
        "Math Problem Set",
        "Essay on Modern Literature",
        "Physics Lab Report",
        "Programming Assignment",
        "History Research Paper"
    )
    val messages = listOf(
        "Hey, I had a question about the deadline for this assignment.",
        "I've completed the initial draft. Would you like to review it?",
        "Can we discuss your bid on my assignment?",
        "Thank you for your help! The solution works perfectly.",
        "I need some clarification on the requirements, please."
    )

    val calendar = Calendar.getInstance()

    return List(8) { index ->
        val daysAgo = Random.nextInt(0, 7)
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
        calendar.add(Calendar.HOUR_OF_DAY, -Random.nextInt(0, 24))
        calendar.add(Calendar.MINUTE, -Random.nextInt(0, 60))

        ChatMessage(
            id = "msg${100 + index}",
            senderName = names[index % names.size],
            message = messages[index % messages.size],
            timestamp = calendar.time,
            unreadCount = if (Random.nextBoolean()) Random.nextInt(1, 5) else 0,
            assignmentTitle = assignments[index % assignments.size],
            isActive = Random.nextBoolean()
        )
    }
}