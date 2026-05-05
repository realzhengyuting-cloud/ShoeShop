package com.example.shoeshop.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoeshop.data.ShoeData
import com.example.shoeshop.model.Shoe
import com.example.shoeshop.ui.components.ShoeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    cartItemCount: Int,
    onShoeClick: (Shoe) -> Unit,
    onCartClick: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ShoeShop",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Box(modifier = Modifier.padding(end = 8.dp)) {
                        IconButton(
                            onClick = onCartClick,
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                Icons.Outlined.ShoppingBag,
                                contentDescription = "Shopping Bag",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        if (cartItemCount > 0) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(x = 2.dp, y = 2.dp)
                                    .size(18.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.tertiary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = cartItemCount.toString(),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Favorites") },
                    label = { Text("Favorites") },
                    selected = false,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = {
                        BadgedBox(
                            badge = {
                                if (cartItemCount > 0) {
                                    Badge { Text(cartItemCount.toString()) }
                                }
                            }
                        ) {
                            Icon(Icons.Outlined.ShoppingBag, contentDescription = "Cart")
                        }
                    },
                    label = { Text("Cart") },
                    selected = false,
                    onClick = onCartClick
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = false,
                    onClick = { }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Discover",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Find your perfect pair",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(ShoeData.shoes) { shoe ->
                    ShoeCard(
                        shoe = shoe,
                        onClick = { onShoeClick(shoe) }
                    )
                }
            }
        }
    }
}
