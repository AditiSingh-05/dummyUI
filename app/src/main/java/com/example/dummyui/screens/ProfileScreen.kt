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
import androidx.compose.material.icons.outlined.*
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dummyui.navigation.AppScreens
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
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
                    selected = false,
                    onClick = { navController.navigate(AppScreens.MessagesScreen.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = true,
                    onClick = { /* Already on profile */ }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            item { ProfileHeader() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { ProfileStatsRow() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { ProfileOverview() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { SkillsSection() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { AssignmentsSection() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { ReviewsSection() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { PortfolioSection() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun ProfileHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "JS",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "John Smith",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Computer Science Student",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "4.8",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = " (24 reviews)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ProfileStatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatCard("12", "Assignments")
        StatCard("8", "Completed")
        StatCard("97%", "On Time")
    }
}

@Composable
fun StatCard(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ProfileOverview() {
    Column {
        SectionHeader("About Me")

        Text(
            text = "Computer Science student at IT with experience in mobile and web development. Passionate about creating efficient solutions to academic challenges.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        SectionHeader("Education")

        EducationItem(
            degree = "Bachelor of Science in Computer Science",
            institution = "Institute of Technology",
            year = "2020 - 2024"
        )

        EducationItem(
            degree = "High School Diploma",
            institution = "Institute of Technology",
            year = "2016 - 2020"
        )
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun SkillsSection() {
    Column {
        SectionHeader("Skills")

        val skills = listOf(
            "Java", "Kotlin", "Android", "Python",
            "JavaScript", "React", "UI/UX Design",
            "Data Structures", "Algorithms"
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ) {
            items(skills) { skill ->
                SuggestionChip(
                    onClick = { },
                    label = { Text(skill) },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        AchievementBadges()
    }
}

@Composable
fun EducationItem(degree: String, institution: String, year: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = degree,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = institution,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = year,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun AchievementBadges() {
    Column {
        SectionHeader("Achievements")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Badge(Icons.Filled.EmojiEvents, "Top Rated")
            Badge(Icons.Filled.Speed, "Fast Delivery")
            Badge(Icons.Filled.Verified, "Verified")
        }
    }
}

@Composable
fun Badge(icon: ImageVector, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                )
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AssignmentsSection() {
    Column {
        SectionHeader("Recent Assignments")

        val currentAssignment = Assignment(
            id = "A101",
            title = "Mobile App Development Project",
            subject = "Computer Science",
            description = "Create a task management app with Kotlin and Jetpack Compose",
            deadlineInDays = 5,
            budgetRange = "$100-$150",
            bidsCount = 3,
            isPostedByMe = false,
            status = AssignmentStatus.IN_PROGRESS
        )

        CurrentAssignmentItem(currentAssignment)

        Spacer(modifier = Modifier.height(8.dp))

        val completedAssignment = Assignment(
            id = "A097",
            title = "Data Structure Implementation",
            subject = "Computer Science",
            description = "Implement a balanced binary search tree in Java",
            deadlineInDays = 0,
            budgetRange = "$80-$120",
            bidsCount = 5,
            isPostedByMe = false,
            status = AssignmentStatus.COMPLETED
        )

        CompletedAssignmentItem(completedAssignment)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentAssignmentItem(assignment: Assignment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* Navigate to assignment details */ }
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
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = 0.65f,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Progress: 65%",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "${assignment.deadlineInDays} days left",
                    style = MaterialTheme.typography.bodySmall,
                    color = if(assignment.deadlineInDays < 3)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedAssignmentItem(assignment: Assignment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* Navigate to assignment details */ }
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
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "5.0",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Completed on May 10, 2023",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ReviewsSection() {
    Column {
        SectionHeader("Reviews")

        RatingSummary()

        Spacer(modifier = Modifier.height(16.dp))

        ReviewItem(
            name = "Alex Johnson",
            rating = 5,
            date = "May 15, 2023",
            comment = "Excellent work on the programming assignment. Delivered ahead of schedule with clean, well-documented code."
        )

        Divider(modifier = Modifier.padding(vertical = 12.dp))

        ReviewItem(
            name = "Sarah Miller",
            rating = 4,
            date = "April 22, 2023",
            comment = "Good job with the math problems. Solutions were clear and easy to understand."
        )

        Button(
            onClick = { /* See all reviews */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("See All Reviews")
        }
    }
}

@Composable
fun RatingSummary() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(0.3f)
                .padding(end = 16.dp)
        ) {
            Text(
                text = "4.8",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Row {
                repeat(5) { index ->
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = if (index < 5) Color(0xFFFFC107) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Text(
                text = "24 reviews",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(modifier = Modifier.weight(0.7f)) {
            RatingBar(5, 18, "5 stars")
            RatingBar(4, 4, "4 stars")
            RatingBar(3, 2, "3 stars")
            RatingBar(2, 0, "2 stars")
            RatingBar(1, 0, "1 star")
        }
    }
}

@Composable
fun RatingBar(stars: Int, count: Int, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.width(48.dp)
        )

        LinearProgressIndicator(
            progress = count.toFloat() / 24f,
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = Color(0xFFFFC107),
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.width(24.dp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun ReviewItem(
    name: String,
    rating: Int,
    date: String,
    comment: String
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(modifier = Modifier.padding(vertical = 4.dp)) {
            repeat(5) { index ->
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = if (index < rating) Color(0xFFFFC107) else MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Text(
            text = comment,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun PortfolioSection() {
    Column {
        SectionHeader("Portfolio")

        PortfolioItem(
            title = "Personal Finance Mobile App",
            description = "Designed and developed a personal finance tracking app using Kotlin and Jetpack Compose",
            tags = listOf("Kotlin", "Compose", "MVVM")
        )

        PortfolioItem(
            title = "E-Learning Platform",
            description = "Created a web-based learning platform with interactive exercises",
            tags = listOf("JavaScript", "React", "Node.js")
        )
    }
}

@Composable
fun PortfolioItem(
    title: String,
    description: String,
    tags: List<String>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tags) { tag ->
                    AssistChip(
                        onClick = { },
                        label = { Text(tag) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    )
                }
            }
        }
    }
}