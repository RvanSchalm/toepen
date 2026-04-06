package nl.ramonvanschalm.toepen.navigation

sealed class Screen(val route: String, val label: String) {
    data object Toepen : Screen("toepen", "Toepen")
    data object Spelers : Screen("spelers", "Spelers")
}
