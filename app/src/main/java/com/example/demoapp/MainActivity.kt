package com.example.demoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.demoapp.ui.theme.DemoAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoAppTheme {
                val context = LocalContext.current.applicationContext
                val viewModel: PlacementViewModel = viewModel(
                    factory = PlacementViewModelFactory(context)
                )

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val tiles by viewModel.tiles.collectAsState()
                    ButtonGrid(
                        tiles = tiles,
                        onTileClick = { index -> viewModel.onTileClicked(index) },
                        onResetClick = { viewModel.onResetClicked() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonGrid(tiles: List<Tile>, onTileClick: (Int) -> Unit,onResetClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Place your tiles!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(10),
        ) {
            itemsIndexed(tiles) { index, tile ->
                IconButton(
                    onClick = { onTileClick(index) },
                    enabled = tile.enabled
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (tile.shape == Shape.CIRCLE) R.drawable.circle else R.drawable.square
                        ),
                        contentDescription = "Tile at index $index",
                        tint = if (tile.shape == Shape.CIRCLE) Color.Gray else Color.Red
                    )
                }
            }
        }
        Button(onClick = { onResetClick() }) {
            Text(text = "Reset")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonGridPreview() {
    DemoAppTheme {
        val tiles = List(100) { Tile() }
        ButtonGrid(tiles = tiles, onTileClick = {}, onResetClick = {})
    }
}
