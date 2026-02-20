package com.example.demoapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

class PlacementViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlacementViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlacementViewModel(GridDataManager(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class PlacementViewModel(private val gridDataManager: GridDataManager) : ViewModel() {

    private val _tiles = MutableStateFlow<List<Tile>>(emptyList())
    val tiles: StateFlow<List<Tile>> = _tiles.asStateFlow()

    init {
        _tiles.value = gridDataManager.loadTiles()
    }

    fun onTileClicked(index: Int) {
        val currentTiles = _tiles.value.toMutableList()
        val clickedTile = currentTiles[index]

        currentTiles[index] = clickedTile.copy(
            shape = Shape.SQUARE,
            enabled = false
        )
        _tiles.value = currentTiles
        saveTiles()
    }

    fun onResetClicked() {
        _tiles.value = List(100) { Tile() }
        saveTiles()
    }

    private fun saveTiles() {
            gridDataManager.saveTiles(_tiles.value)
    }
}
