package com.example.shoeshop.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shoeshop.model.CartItem
import com.example.shoeshop.model.Shoe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addToCart(shoe: Shoe) {
        val current = _cartItems.value
        val existing = current.find { it.shoe.id == shoe.id }
        _cartItems.value = if (existing != null) {
            current.map {
                if (it.shoe.id == shoe.id) it.copy(quantity = it.quantity + 1)
                else it
            }
        } else {
            current + CartItem(shoe)
        }
    }

    fun increment(shoeId: Int) {
        _cartItems.value = _cartItems.value.map {
            if (it.shoe.id == shoeId) it.copy(quantity = it.quantity + 1)
            else it
        }
    }

    fun decrement(shoeId: Int) {
        _cartItems.value = _cartItems.value.mapNotNull {
            if (it.shoe.id == shoeId) {
                if (it.quantity > 1) it.copy(quantity = it.quantity - 1) else null
            } else it
        }
    }

    fun remove(shoeId: Int) {
        _cartItems.value = _cartItems.value.filter { it.shoe.id != shoeId }
    }
}
