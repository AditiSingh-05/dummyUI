package com.example.dummyui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dummyui.navigation.AppScreens
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

data class Assignment(
    val id: String,
    val title: String,
    val subject: String,
    val description: String = "",
    val deadlineInDays: Int,
    val budgetRange: String,
    val bidsCount: Int = 0,
    val isPostedByMe: Boolean = false,
    val status: AssignmentStatus = AssignmentStatus.ACTIVE
)

enum class AssignmentStatus {
    ACTIVE, IN_PROGRESS, PENDING_REVIEW, COMPLETED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = getGreeting(),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = getCurrentDate(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { /* Notifications */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = { /* Already on home */ }
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
                    selected = false,
                    onClick = { navController.navigate(AppScreens.MessagesScreen.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = false,
                    onClick = { navController.navigate(AppScreens.ProfileScreen.route) }
                )
            }
        },
        floatingActionButton = {
            Box(
                modifier = Modifier.offset(y = 35.dp) // Adjust this value to control overlap
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate(AppScreens.CreateAssignmentScreen.route) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Create Assignment",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // Stats Card
            item { StatsCard() }

            // My Assignments Section
            item { MyAssignmentsSection(navController) }

            // Available Assignments Section
            item { AvailableAssignmentsSection(navController) }

            // Recent Activity Section
            item { RecentActivitySection() }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun StatsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Your Statistics",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(
                    icon = Icons.Default.Create,
                    label = "Posted",
                    value = "3",
                    color = MaterialTheme.colorScheme.primary
                )
                StatItem(
                    icon = Icons.Default.Assignment,
                    label = "Working On",
                    value = "2",
                    color = Color(0xFFFFA000)
                )
                StatItem(
                    icon = Icons.Default.CheckCircle,
                    label = "Completed",
                    value = "5",
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}

@Composable
private fun StatItem(
    icon: ImageVector,
    label: String,
    value: String,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = color)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MyAssignmentsSection(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Assignments",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            TextButton(onClick = { navController.navigate(AppScreens.AssignmentsScreen.route) }) {
                Text("See All")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        val myAssignments = generateSampleAssignments(3, true)

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(myAssignments) { assignment ->
                AssignmentCard(assignment)
            }
        }
    }
}

@Composable
private fun AvailableAssignmentsSection(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Available Assignments",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            TextButton(onClick = { navController.navigate(AppScreens.AssignmentsScreen.route) }) {
                Text("See All")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        val availableAssignments = generateSampleAssignments(3, false)

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(availableAssignments) { assignment ->
                AssignmentCard(assignment)
            }
        }
    }
}

@Composable
private fun AssignmentCard(assignment: Assignment) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = assignment.subject,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                StatusChip(assignment.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = assignment.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoChip(Icons.Default.Timer, "${assignment.deadlineInDays} days")
                InfoChip(Icons.Default.MonetizationOn, assignment.budgetRange)
                InfoChip(Icons.Default.People, "${assignment.bidsCount} bids")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* View details */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(if (assignment.isPostedByMe) "Manage" else "Apply")
            }
        }
    }
}

@Composable
fun InfoChip(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun StatusChip(status: AssignmentStatus) {
    val (backgroundColor, textColor) = when(status) {
        AssignmentStatus.ACTIVE -> Color(0xFF4CAF50).copy(alpha = 0.1f) to Color(0xFF4CAF50)
        AssignmentStatus.IN_PROGRESS -> Color(0xFFFFA000).copy(alpha = 0.1f) to Color(0xFFFFA000)
        AssignmentStatus.PENDING_REVIEW -> Color(0xFF2196F3).copy(alpha = 0.1f) to Color(0xFF2196F3)
        AssignmentStatus.COMPLETED -> Color(0xFF9C27B0).copy(alpha = 0.1f) to Color(0xFF9C27B0)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = status.name.replace("_", " "),
            style = MaterialTheme.typography.bodySmall,
            color = textColor
        )
    }
}
@Composable
private fun RecentActivitySection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Recent Activity",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                ActivityItem("New bid on 'Math Problem Set'", "Review")
                Divider()
                ActivityItem("Assignment 'Essay Writing' completed", "Rate")
                Divider()
                ActivityItem("Message from Alex about 'Programming Help'", "Reply")
            }
        }
    }
}

@Composable
private fun ActivityItem(description: String, actionLabel: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

        TextButton(
            onClick = { /* Handle action */ },
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(actionLabel)
        }
    }
}
private fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())
    return dateFormat.format(Date())
}

private fun getGreeting(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when {
        hour < 12 -> "Good Morning"
        hour < 17 -> "Good Afternoon"
        else -> "Good Evening"
    }
}

fun generateSampleAssignments(count: Int, isPostedByMe: Boolean): List<Assignment> {
    val subjects = listOf("Mathematics", "Computer Science", "History", "Physics", "Biology", "Literature")
    val statuses = AssignmentStatus.values()

    return List(count) { index ->
        Assignment(
            id = "A${100 + index}",
            title = if (isPostedByMe)
                "My ${subjects[index % subjects.size]} Assignment ${index + 1}"
            else
                "${subjects[index % subjects.size]} Problem Set ${index + 1}",
            subject = subjects[index % subjects.size],
            deadlineInDays = Random.nextInt(1, 14),
            budgetRange = "$${Random.nextInt(20, 100)}-$${Random.nextInt(100, 200)}",
            bidsCount = if (isPostedByMe) Random.nextInt(0, 10) else 0,
            isPostedByMe = isPostedByMe,
            status = if (isPostedByMe)
                statuses[Random.nextInt(statuses.size)]
            else
                AssignmentStatus.ACTIVE
        )
    }
}

