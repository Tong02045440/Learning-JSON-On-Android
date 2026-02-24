package com.example.demoapp

import android.content.Context
import android.util.Log
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

private const val PLACEMENT_SAVES = "placement_saves"
private const val TILES_LIST_KEY = "tiles_list"
private const val JSON_FILENAME = "tiles.json"

class GridDataManager(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PLACEMENT_SAVES, Context.MODE_PRIVATE)
    private val prettyJson = Json { prettyPrint = true }

    fun saveTiles(tiles: List<Tile>) {
        val jsonString = Json.encodeToString(tiles)
        val prettyJsonString = prettyJson.encodeToString(tiles)
        val file = File(context.filesDir, JSON_FILENAME)

        sharedPreferences.edit().putString(TILES_LIST_KEY, jsonString).apply()

        FileOutputStream(file).use {
            it.write(prettyJsonString.toByteArray())
        }
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