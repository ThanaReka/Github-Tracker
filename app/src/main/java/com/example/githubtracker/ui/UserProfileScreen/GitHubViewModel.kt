package com.example.githubtracker.ui.UserProfileScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.githubtracker.data.GitHubRepository
import com.example.githubtracker.model.Repo
import com.example.githubtracker.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed class GitUiState {
    data class Success(val user: User, val repos: List<Repo>) : GitUiState()
    data class Error(val error: String) : GitUiState()
    object Loading : GitUiState()
}

@HiltViewModel
class GitHubViewModel @Inject constructor(private val gitHubRepository: GitHubRepository) : ViewModel() {

    private val _selectedRepo = MutableStateFlow<Repo?>(null)
    val selectedRepo: StateFlow<Repo?> = _selectedRepo

    private val _totalForks = mutableStateOf(0)
    val totalForks: State<Int> = _totalForks

    var gitUiState: GitUiState by mutableStateOf(GitUiState.Loading)
        private set


    fun fetchUser(userId: String) {
        viewModelScope.launch {
            gitUiState = try {
                val user = gitHubRepository.getUser(userId)
                val repos = gitHubRepository.getUserRepos(userId)
                _totalForks.value = repos.sumOf { it.forks }
                GitUiState.Success(user, repos)
            } catch (e: IOException) {
                GitUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                GitUiState.Error(e.message.toString())
            }
        }
    }

    fun selectRepo(cardData: Repo) {
        _selectedRepo.value = cardData
    }
}

