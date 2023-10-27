package fr.eurosport.sportstories.feature_media.presentation.routing

/**
 * Class defining all possible screens in the app.
 */
sealed class Screen(
    val route: String
) {
    companion object {
        fun fromRoute(route: String?): Screen {
            return when (route) {
                Medias.route -> Medias
                Story.route -> Story
                Video.route -> Video
                else -> Medias
            }
        }
    }

    data object Medias : Screen("Medias")
    data object Story : Screen("Story")
    data object Video : Screen("Video")
}