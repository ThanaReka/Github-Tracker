package com.example.githubtracker

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.githubtracker.ui.DetailScreen.DetailScreen
import com.example.githubtracker.ui.UserProfileScreen.GitHubViewModel
import com.example.githubtracker.ui.UserProfileScreen.UserProfileScreen

enum class GitHubScreens(@StringRes val title: Int){
    Start(title = R.string.start),
    DetailScreen(title = R.string.detail_screen),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreensAppBar(
    currentScreen: GitHubScreens,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
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
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        tint = colorResource(id = R.color.white),
                    )
                }
            }
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: GitHubViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = GitHubScreens.valueOf(
        backStackEntry?.destination?.route ?: GitHubScreens.Start.name
    )

    Scaffold(
        topBar = {
            ScreensAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp()}
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            NavHost(
                navController = navController,
                startDestination = GitHubScreens.Start.name
            ) {
                composable(route = GitHubScreens.Start.name) {
                    UserProfileScreen(viewModel, modifier, navController)
                }
                composable(route = GitHubScreens.DetailScreen.name) {
                    DetailScreen(
                        Modifier,
                        viewModel,
                        navigateUp = { navController.popBackStack(GitHubScreens.DetailScreen.name, inclusive = false) },
                    )
                }

            }
        }
    }

}

