package com.mozzartbet.grckikino.ui.navigation

import androidx.navigation.NavController

fun NavController.navigateToSingleTop(route: String) {
    this.navigate(route) {
        launchSingleTop = true
    }
}