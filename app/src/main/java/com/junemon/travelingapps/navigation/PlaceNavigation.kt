package com.junemon.travelingapps.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.junemon.travelingapps.screen.detail.DetailScreen
import com.junemon.travelingapps.screen.home.HomeScreen

@Composable
fun PlaceNavigation(navController: NavHostController) {
    NavHost(navController = navController,
        startDestination = PlaceDestination.LoadHome().name,
        builder = {
            composable(PlaceDestination.LoadHome().name) {
                HomeScreen(modifier = Modifier.fillMaxSize()) { selectedPlaceId, selectedPlaceType ->
                    navController.navigate("${PlaceDestination.LoadDetail().name}/$selectedPlaceId/$selectedPlaceType")
                }
            }

            composable(
                "${PlaceDestination.LoadDetail().name}/{${PlaceDestinationArgument.DetailPlacesId.name}}/{${PlaceDestinationArgument.DetailPlacesType.name}}",
                arguments = listOf(
                    navArgument(PlaceDestinationArgument.DetailPlacesId.name) {
                        type = NavType.IntType
                    },
                    navArgument(PlaceDestinationArgument.DetailPlacesType.name) {
                        type = NavType.StringType
                    }
                )
            ) {
                DetailScreen(onSelectedSimilarPlaces = { selectedPlaceId, selectedPlaceType ->
                    navController.navigate("${PlaceDestination.LoadDetail().name}/$selectedPlaceId/$selectedPlaceType")
                }, onUpButtonPress = {
                    navController.popBackStack()
                })
            }
        })

}