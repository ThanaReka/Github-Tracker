package com.example.githubtracker.ui.DetailScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubtracker.R
import com.example.githubtracker.ui.UserProfileScreen.GitHubViewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: GitHubViewModel = hiltViewModel(),
) {
    val selectedRepo by viewModel.selectedRepo.collectAsState()

    val totalForks by viewModel.totalForks

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                modifier = modifier
                    .padding(
                        vertical = dimensionResource(id = R.dimen.smallest_padding),
                        horizontal = dimensionResource(id = R.dimen.small_padding)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.small_padding))
            )
            {
                Text(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.large_padding)),
                    text = "Selected Repository: ${selectedRepo?.name ?: "No name"}",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.small_padding)),
                    text = "Repository Description: ${selectedRepo?.description ?: "No description"}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.small_padding)),
                    text = "Updated at: ${selectedRepo?.updated_at ?: "No date available"}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.small_padding)),
                    text = "Stargazers count: ${selectedRepo?.stargazers_count ?: "No stargazers count available"} stargazer(s)",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.small_padding)),
                    text = "Forks count: ${selectedRepo?.forks.toString()} forks",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
                Text(
                    modifier = Modifier.padding(48.dp),
                    text = "Total Forks for user: $totalForks",
                    color = if (totalForks > 5000) Color.Red else Color.Black,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )


            }
        }
    }



