package fr.eurosport.sportstories.feature_media.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fr.eurosport.sportstories.common.theme.SportStoriesTheme
import fr.eurosport.sportstories.common.util.UIEvent
import fr.eurosport.sportstories.feature_media.presentation.modelUi.MediaElementUI
import fr.eurosport.sportstories.feature_media.presentation.modelUi.MediaElementUI.StoryUI
import fr.eurosport.sportstories.feature_media.presentation.modelUi.MediaElementUI.VideoUI
import fr.eurosport.sportstories.feature_media.presentation.routing.Screen
import fr.eurosport.sportstories.feature_media.presentation.screens.MediaListScreen
import fr.eurosport.sportstories.feature_media.presentation.screens.StoryScreen
import fr.eurosport.sportstories.feature_media.presentation.screens.VideoScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            SportStoriesTheme {
                val viewModel = viewModel<HomeViewModel>()
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                DisplayErrorMessageIfAny(viewModel)
                MainActivityScreen(navController, viewModel)
            }
        }
    }

    @Composable
    private fun DisplayErrorMessageIfAny(
        viewModel: HomeViewModel
    ) {
        val context = LocalContext.current
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        LaunchedEffect(key1 = Unit) {
            viewModel.eventFlow.flowWithLifecycle(lifecycle).collect { uiEvent ->
                when (uiEvent) {
                    is UIEvent.ShowError -> {
                        Toast.makeText(context, uiEvent.errorStrRes, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    @Composable
    private fun MainActivityScreen(
        navController: NavHostController, viewModel: HomeViewModel
    ) {
        NavHost(
            navController = navController, startDestination = Screen.Medias.route
        ) {
            composable(Screen.Medias.route) {
                HomeContent(viewModel) {
                    when (it) {
                        is StoryUI -> navController.navigate(Screen.Story.route)
                        is VideoUI -> navController.navigate(Screen.Video.route)
                    }
                }
            }
            composable(Screen.Story.route) {
                StoryScreen(
                    viewModel = viewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onShare = {

                    }
                )
            }
            composable(Screen.Video.route) {
                VideoScreen(
                    viewModel = viewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }

    @Composable
    private fun HomeContent(
        viewModel: HomeViewModel, onNavigateToMedia: (MediaElementUI) -> Unit = {}
    ) {
        MediaListScreen(viewModel) { selectedMedia ->
            viewModel.setSelectedMedia(selectedMedia)
            onNavigateToMedia.invoke(selectedMedia)
        }
    }
}