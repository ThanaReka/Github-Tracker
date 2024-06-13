import com.example.githubtracker.data.NetworkGitHubRepository
import com.example.githubtracker.fake.FakeGitHubApiService
import com.example.githubtracker.model.Repo
import com.example.githubtracker.model.User
import com.example.githubtracker.network.GitHubApiService
import com.example.githubtracker.rules.TestDispatcherRule
import com.example.githubtracker.ui.UserProfileScreen.GitHubViewModel
import com.example.githubtracker.ui.UserProfileScreen.GitUiState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class GitHubViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var viewModel: GitHubViewModel
    private val fakeRepository = NetworkGitHubRepository(FakeGitHubApiService())

    @Before
    fun setup() {
        viewModel = GitHubViewModel(fakeRepository)
    }

    @Test
    fun testFetchUserSuccess() = runTest {
        viewModel.fetchUser("testuser")
        val uiState = viewModel.gitUiState
        assertTrue(uiState is GitUiState.Success)
        uiState as GitUiState.Success
        assertEquals("Test User", uiState.user.name)
        assertEquals(2, uiState.repos.size)
    }

    @Test
    fun testFetchUserError() = runTest {
        // Modify the fake service to throw an exception for this test
        val errorService = object : GitHubApiService {
            override suspend fun getUser(userId: String): User {
                throw IOException("Test exception")
            }

            override suspend fun getUserRepos(userId: String): List<Repo> {
                return emptyList()
            }
        }
        val errorRepository = NetworkGitHubRepository(errorService)
        val errorViewModel = GitHubViewModel(errorRepository)

        errorViewModel.fetchUser("testuser")
        val uiState = errorViewModel.gitUiState
        assertTrue(uiState is GitUiState.Error)
        uiState as GitUiState.Error
        assertEquals("Test exception", uiState.error)
    }
}
