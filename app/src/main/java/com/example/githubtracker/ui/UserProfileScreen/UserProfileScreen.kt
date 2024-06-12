package com.example.githubtracker.ui.UserProfileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.githubtracker.R
import com.example.githubtracker.data.GitHubRepository
import com.example.githubtracker.model.Repo
import com.example.githubtracker.ui.GitHubScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    viewModel: GitHubViewModel = viewModel(factory = GitHubViewModel.provideFactory(repository = GitHubRepository())),
    modifier: Modifier = Modifier,
    navController: NavHostController,

) {
    var userId by remember { mutableStateOf("") }

    val gitUiState = viewModel.gitUiState

    Scaffold(
        topBar = {
            ScreensAppBar()
        }
    ) { it ->
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            SearchBar(userId, onValueChange = { userId = it }, onSearchClick = { viewModel.fetchUser(userId) })
            when (gitUiState) {
                is GitUiState.Loading -> {
//                    LoadingScreen()
                }
                is GitUiState.Success -> {
                    GitReposList(viewModel, gitUiState, Modifier, navController)
                }
                is GitUiState.Error -> {
                    ErrorScreen()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    userId: String,
    onValueChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = userId,
            onValueChange = onValueChange,
            label = { Text(stringResource(R.string.enter_id_label)) },
            modifier = Modifier.padding(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
            )
        )
        FilledTonalButton(
            onClick = onSearchClick,
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
        ) {
            Text(
                stringResource(R.string.search),
                color = Color.Black,
                maxLines = 1,
                softWrap = false
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
            modifier = Modifier
                .fillMaxSize() // Makes the Box fill the entire screen
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center) // Centers the Column within the Box
        ) {
            Image(
                modifier = Modifier.size(dimensionResource(id = R.dimen.loading_img_size)),
                painter = painterResource(R.drawable.baseline_person_search_24),
                contentDescription = stringResource(R.string.loading)
            )
        }
    }
}

@Composable
fun GitReposList(
    viewModel: GitHubViewModel = viewModel(factory = GitHubViewModel.provideFactory(repository = GitHubRepository())),
    gitUiState: GitUiState.Success,
    modifier: Modifier = Modifier,
    navController: NavHostController,
){
    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement= Arrangement.Center,
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(gitUiState?.user?.avatar_url ?: "Image not Available")
                        .build(),
                    placeholder = painterResource(R.drawable.broken_image),
                    contentDescription = stringResource(R.string.avatar_img),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.avatar_size))
                        .padding(dimensionResource(id = R.dimen.small_padding), 0.dp)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = gitUiState?.user?.name ?: "User not found",
                    color = Color.Black
                )
            }
        }
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(gitUiState.repos) { repo ->
                RepoItemCard(
                    repo,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        viewModel.selectRepo(repo)
                        navController.navigate(GitHubScreens.DetailScreen.name)
                    }
                )
            }
        }
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement= Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_error_24), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(dimensionResource(id = R.dimen.large_padding)))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreensAppBar(
    modifier: Modifier = Modifier
){
//    Surface(shadowElevation = 32.dp) {
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
//    }
}


@Composable
fun RepoItemCard(
    repo: Repo,
    modifier: Modifier = Modifier,
    onClick: (Repo) -> Unit
) {
    Card(
        modifier = modifier
            .padding(
                vertical = dimensionResource(id = R.dimen.smallest_padding),
                horizontal = dimensionResource(id = R.dimen.small_padding)
            )
            .clickable {
                onClick(repo)
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.small_padding))
    )
    {
            Column {
                Text(
                    text = repo.name,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.small_padding)),
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = repo.description ?: "No description",
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.small_padding))
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
    }
}




//@Preview(showBackground = true)
//@Composable
//fun GitInfoLayoutPreview(){
//    GitInfoLayout()
//}
//
//@Preview(showBackground = true)
//@Composable
//fun CardPreview(){
//    ListItemCard(
//        GitInfoRepository.gitinfolist
//    )
//}