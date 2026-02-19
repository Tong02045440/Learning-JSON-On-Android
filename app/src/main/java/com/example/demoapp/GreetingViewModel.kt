package com.example.demoapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class GreetingUiState(
    val displayName: String = "Android",
    val userInput: String = ""
)

class GreetingViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(GreetingUiState())
    val uiState: StateFlow<GreetingUiState> = _uiState.asStateFlow()

    fun onUpdateGreetingClicked() {
        if (uiState.value.userInput.isNotBlank()) {
            val reversedName = uiState.value.userInput.reversed()
            _uiState.update { currentState ->
                currentState.copy(displayName = reversedName)
            }
        }
    }

    fun onUserInputChanged(newInput: String) {
        _uiState.update { currentState ->
            currentState.copy(userInput = newInput)
        }
    }
}