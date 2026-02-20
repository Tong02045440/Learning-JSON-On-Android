package com.example.demoapp

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class BoardUiState(
    val board: Board = Board(cols = 10, rows = 10, ships = emptyList()),
    val selectedShipToPlace: Ship? = null
)

data class Board(
    val cols: Int,
    val rows: Int,
    val ships: List<Ship>
)

data class Ship(
    val type: ShipType,
    val x: Int,
    val y: Int,
    val facing: Direction,
)

data class ShipType(
    val size: Int,
    val color: Color
)

enum class Direction{
    UP,
    DOWN,
    LEFT,
    RIGHT
}


class PlacementViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BoardUiState())
    val uiState: StateFlow<BoardUiState> = _uiState.asStateFlow()

}