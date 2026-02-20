package com.example.demoapp

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

enum class Shape {
    CIRCLE, SQUARE
}

data class Tile(
    val shape: Shape = Shape.CIRCLE,
    val color: Color = Color(0xFF90A4AE),
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

        if (clickedTile.shape == Shape.CIRCLE) {
            val randomColor = Color(
                red = Random.nextFloat(),
                green = Random.nextFloat(),
                blue = Random.nextFloat(),
            )
            currentTiles[index] = clickedTile.copy(
                shape = Shape.SQUARE,
                color = randomColor,
                enabled = false
            )
            _tiles.value = currentTiles
        }
    }
}
