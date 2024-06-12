package com.example.githubtracker.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.githubtracker.data.GitHubRepository
import com.example.githubtracker.ui.DetailScreen.DetailScreen
import com.example.githubtracker.ui.UserProfileScreen.GitHubViewModel
import com.example.githubtracker.ui.UserProfileScreen.UserProfileScreen

enum class GitHubScreens(val title: String){
    UserProfile(title = "User Profile Screen"),
    DetailScreen(title = "Detail Screen"),
}

@Composable
fun MainScreen(
    viewModel: GitHubViewModel = viewModel(factory = GitHubViewModel.provideFactory(repository = GitHubRepository())),
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = GitHubScreens.UserProfile.name
    ) {
        composable(route = GitHubScreens.UserProfile.name) {
            UserProfileScreen(viewModel, modifier, navController)
        }
        composable(route = GitHubScreens.DetailScreen.name) {
            DetailScreen(
                Modifier,
                viewModel,
//                navigateUp = { navController.popBackStack(NewzScreen.RSSFeed.name, inclusive = false) },
                navController
            )
        }

    }

}