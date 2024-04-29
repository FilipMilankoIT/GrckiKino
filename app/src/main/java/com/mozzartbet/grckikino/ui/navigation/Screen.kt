package com.mozzartbet.grckikino.ui.navigation

enum class Screen(val route: String, val argumentKey: String? = null) {
    NEXT_DRAWS_SCREEN("next_draws_screen"),
    LIVE_DRAWS_SCREEN("live_draws_screen"),
    DRAW_RESULTS_SCREEN("draw_results_screen"),
    DRAW_SCREEN("draw_screen", "draw_id")
}