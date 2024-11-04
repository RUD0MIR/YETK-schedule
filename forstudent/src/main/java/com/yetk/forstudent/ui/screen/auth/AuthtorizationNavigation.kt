package com.yetk.forstudent.ui.screen.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val AUTH_NAV_ROUTE = "authorization"

fun NavController.navigateToAuthorization(navOptions: NavOptions? = null) {
    this.navigate(AUTH_NAV_ROUTE, navOptions)
}

fun NavGraphBuilder.authorizationScreen(onNavigate: () -> Unit) {
    composable(
        route = AUTH_NAV_ROUTE
    ) {
        AuthorizationRoute(onNavigate)
    }
}
