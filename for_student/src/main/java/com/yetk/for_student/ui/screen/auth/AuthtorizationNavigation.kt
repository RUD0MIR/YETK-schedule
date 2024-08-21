package com.yetk.for_student.ui.screen.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val authorizationNavigationRoute = "authorization"

fun NavController.navigateToAuthorization(navOptions: NavOptions? = null) {
    this.navigate(authorizationNavigationRoute, navOptions)
}

fun NavGraphBuilder.authorizationScreen(
    onNavigate: () -> Unit
) {
    composable(
        route = authorizationNavigationRoute,
    ) {
        AuthorizationRoute(onNavigate)
    }
}