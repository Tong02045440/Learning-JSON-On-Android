package com.example.demoapp

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val PLACEMENT_SAVES = "placement_saves"
private const val TILES_LIST_KEY = "tiles_list"

class GridDataManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PLACEMENT_SAVES, Context.MODE_PRIVATE)

    fun saveTiles(tiles: List<Tile>) {
        val jsonString = Json.encodeToString(tiles)
        sharedPreferences.edit().putString(TILES_LIST_KEY, jsonString).apply()
    }

    fun loadTiles(): List<Tile> {
        val jsonString = sharedPreferences.getString(TILES_LIST_KEY, null)
        return if (jsonString != null) {
            Json.decodeFromString<List<Tile>>(jsonString)
        } else {
            List(100) { Tile() }
        }
    }
}