package com.example.shoeshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.shoeshop.data.ShoeData
import com.example.shoeshop.navigation.CartRoute
import com.example.shoeshop.navigation.DetailRoute
import com.example.shoeshop.navigation.HomeRoute
import com.example.shoeshop.ui.screens.CartScreen
import com.example.shoeshop.ui.screens.DetailScreen
import com.example.shoeshop.ui.screens.HomeScreen
import com.example.shoeshop.ui.theme.ShoeShopTheme
import com.example.shoeshop.viewmodel.CartViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ShoeShopTheme {
                ShoeShopApp()
            }
        }
    }
}

@Composable
fun ShoeShopApp(cartViewModel: CartViewModel = viewModel()) {
    val navController = rememberNavController()
    val cartItems by cartViewModel.cartItems.collectAsStateWithLifecycle()
    val cartItemCount = cartItems.sumOf { it.quantity }

    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        enterTransition = {
            fadeIn(animationSpec = tween(300)) + slideInHorizontally { it / 4 }
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300)) + slideOutHorizontally { -it / 4 }
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300)) + slideInHorizontally { -it / 4 }
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300)) + slideOutHorizontally { it / 4 }
        }
    ) {
        composable<HomeRoute> {
            HomeScreen(
                cartItemCount = cartItemCount,
                onShoeClick = { shoe ->
                    navController.navigate(DetailRoute(shoeId = shoe.id))
                },
                onCartClick = { navController.navigate(CartRoute) }
            )
        }

        composable<DetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<DetailRoute>()
            val shoe = ShoeData.shoes.first { it.id == route.shoeId }
            DetailScreen(
                shoe = shoe,
                onBackClick = { navController.popBackStack() },
                onAddToCart = { addedShoe ->
                    cartViewModel.addToCart(addedShoe)
                    navController.navigate(CartRoute)
                }
            )
        }

        composable<CartRoute> {
            CartScreen(
                cartItems = cartItems,
                onBackClick = { navController.popBackStack() },
                onIncrement = { shoeId -> cartViewModel.increment(shoeId) },
                onDecrement = { shoeId -> cartViewModel.decrement(shoeId) },
                onRemove = { shoeId -> cartViewModel.remove(shoeId) }
            )
        }
    }
}
