package com.example.demoapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable


enum class Shape {
    CIRCLE, SQUARE
}

@Serializable
data class Tile(
    val shape: Shape = Shape.CIRCLE,
    val enabled: Boolean = true
)

class PlacementViewModel : ViewModel() {

    private val _tiles = MutableStateFlow<List<Tile>>(emptyList())
    val tiles: StateFlow<List<Tile>> = _tiles.asStateFlow()

    init {
        _tiles.value = List(100) { Tile() }
    }

    fun onTileClicked(index: Int) {
        val currentTiles = _tiles.value.toMutableList()
        val clickedTile = currentTiles[index]

        currentTiles[index] = clickedTile.copy(
            shape = Shape.SQUARE,
            enabled = false
        )
        _tiles.value = currentTiles
    }

    fun onResetClicked() {
        _tiles.value = List(100) { Tile() }
    }
}
