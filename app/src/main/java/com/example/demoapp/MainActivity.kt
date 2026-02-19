package com.example.demoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.demoapp.ui.theme.DemoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoAppTheme {

                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "greeting",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = "greeting") {
                            GreetingScreen(
                                onNavigateToSecondScreen = { name ->
                                    navController.navigate("second_screen?name=$name")
                                }
                            )
                        }

                        composable(
                            route = "second_screen?name={name}"
                            /*arguments = listOf(
                                navArgument("name") {
                                    type = NavType.StringType
                                    nullable = true
                                    defaultValue = "Guest"
                                }
                            )*/
                        ) { backStackEntry ->
                            var name = backStackEntry.arguments?.getString("name") ?: "Guest"
                            if (name.isBlank()) name = "Guest"
                            SecondScreen(
                                name = name,
                                onNavigateUp = { navController.navigateUp() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GreetingScreen(
    modifier: Modifier = Modifier,
    greetingViewModel: GreetingViewModel = viewModel(),
    onNavigateToSecondScreen: (String) -> Unit
) {

    val uiState by greetingViewModel.uiState.collectAsState()

    Greeting(
        modifier = modifier,
        displayName = uiState.displayName,
        userInput = uiState.userInput,
        onUpdateGreetingClicked = { greetingViewModel.onUpdateGreetingClicked() },
        onUserInputChanged = { newText -> greetingViewModel.onUserInputChanged(newText) },
        onNavigateToSecondScreen = { onNavigateToSecondScreen(uiState.userInput) }
    )
}

@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    displayName: String,
    userInput: String,
    onUpdateGreetingClicked: () -> Unit,
    onUserInputChanged: (String) -> Unit,
    onNavigateToSecondScreen: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Hello $displayName!")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = userInput,
            onValueChange = onUserInputChanged,
            label = { Text("Enter a name") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onUpdateGreetingClicked) {
            Text("Reverse and Update Name")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onNavigateToSecondScreen) {
            Text("Go to Second Screen")
        }
    }
}

@Composable
fun SecondScreen(
    name: String,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome to the Second Screen, $name!")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateUp) {
            Text("Go Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoAppTheme {
        Greeting(
            displayName = "Android",
            userInput = "Test",
            onUpdateGreetingClicked = {},
            onUserInputChanged = {},
            onNavigateToSecondScreen = {}
        )
    }
}