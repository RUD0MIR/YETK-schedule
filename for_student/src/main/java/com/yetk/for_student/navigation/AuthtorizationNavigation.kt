package com.yetk.for_student.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yetk.for_student.screen.AuthorizationRoute

const val AuthorizationNavigationRoute = "authorization"

fun NavController.navigateToAuthorization(navOptions: NavOptions? = null) {
    this.navigate(AuthorizationNavigationRoute, navOptions)
}

fun NavGraphBuilder.authorizationScreen(
) {
    composable(
        route = AuthorizationNavigationRoute,
    ) {
        AuthorizationRoute()
    }
}