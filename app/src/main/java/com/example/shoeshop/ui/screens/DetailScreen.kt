package com.example.shoeshop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shoeshop.model.Shoe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    shoe: Shoe,
    onBackClick: () -> Unit,
    onAddToCart: (Shoe) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = shoe.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(22.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            modifier = Modifier.size(22.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 12.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .navigationBarsPadding(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Price",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$${shoe.price}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = { onAddToCart(shoe) },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                    ) {
                        Text(
                            "Add to Bag",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                AsyncImage(
                    model = shoe.imageUrl,
                    contentDescription = shoe.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                Text(
                    text = shoe.brand.uppercase(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 1.5.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = shoe.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = shoe.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Size",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    listOf("7", "8", "9", "10", "11").forEachIndexed { index, size ->
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (index == 2) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surface
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = size,
                                style = MaterialTheme.typography.labelLarge,
                                color = if (index == 2) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
