package com.example.rickmorty.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rickmorty.components.screens.CharacterDetailsScreen
import com.example.rickmorty.components.screens.CharacterEpisodeScreen
import com.example.rickmorty.components.screens.CharactersScreen

@Composable
fun RickAndMortyNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "characters") {
        composable(route = "characters") {
            CharactersScreen(
                onItemClick = { characterId ->
                    navController.navigate("character_details/$characterId")
                }
            )
        }
        composable(
            route = "character_details/{characterId}",
            arguments = listOf(navArgument("characterId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val characterId: Int = backStackEntry.arguments?.getInt("characterId") ?: -1
            CharacterDetailsScreen(characterId = characterId) {
                navController.navigate("character_episodes/$it")
            }
        }
        composable(
            route = "character_episodes/{characterId}",
            arguments = listOf(navArgument("characterId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val characterId: Int = backStackEntry.arguments?.getInt("characterId") ?: -1
            CharacterEpisodeScreen(characterId = characterId)
        }
    }
}