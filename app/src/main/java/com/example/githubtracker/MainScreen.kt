package com.example.githubtracker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.githubtracker.ui.DetailScreen.DetailScreen
import com.example.githubtracker.ui.UserProfileScreen.GitHubViewModel
import com.example.githubtracker.ui.UserProfileScreen.UserProfileScreen

enum class GitHubScreens(val title: String){
    UserProfile(title = "User Profile Screen"),
    DetailScreen(title = "Detail Screen"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: GitHubViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()
    Scaffold(
        topBar = {
            ScreensAppBar()
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
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
                    )
                }

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreensAppBar(
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.White
            )
        },
        modifier = modifier
            .shadow(elevation = 8.dp, shape = MaterialTheme.shapes.medium, clip = false),
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF3f51b5)),
    )
}