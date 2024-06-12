package com.example.githubtracker.ui.DetailScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.githubtracker.data.GitHubRepository
import com.example.githubtracker.ui.UserProfileScreen.GitHubViewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: GitHubViewModel = viewModel(factory = GitHubViewModel.provideFactory(repository = GitHubRepository())),
//    navigateUp: () -> Unit,
    navController: NavHostController,
) {
    val selectedRepo by viewModel.selectedRepo.collectAsState()

    val totalForks by viewModel.totalForks

    Box(
        modifier = Modifier
            .fillMaxSize() // Makes the Box fill the entire screen
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center) // Centers the Column within the Box
                .padding(16.dp), // Optional padding for the Column
            horizontalAlignment  = Alignment.CenterHorizontally// Optional padding for the Column
        ) {
            //        AsyncImage(
            //            model = ImageRequest.Builder(context = LocalContext.current)
            //                .data(selectedRepo?.user?.avatar_url ?: "Image not Available")
            //                .build(),
            //            placeholder = painterResource(R.drawable.broken_image),
            //            contentDescription = stringResource(R.string.avatar_img),
            //            modifier = Modifier
            //                .size(dimensionResource(id = R.dimen.avatar_size))
            //                .padding(dimensionResource(id = R.dimen.small_padding), 0.dp)
            //                .aspectRatio(1f),
            //            contentScale = ContentScale.Crop
            //        )

            Text(
                modifier = Modifier.padding(16.dp),
                text = selectedRepo?.name ?: "No name",
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = selectedRepo?.description ?: "No description",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = selectedRepo?.updated_at ?: "No date available",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = "${selectedRepo?.stargazers_count ?: "No stargazers count"} stargazers",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = "${selectedRepo?.forks.toString()} forks",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.padding(48.dp),
                text = "Total Forks: $totalForks",
                color = if (totalForks > 5000) Color.Red else Color.Black,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium,
            )

        }
    }
}

