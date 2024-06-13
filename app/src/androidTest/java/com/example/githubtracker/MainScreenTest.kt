import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.githubtracker.FakeGitHubApiService
import com.example.githubtracker.data.NetworkGitHubRepository
import com.example.githubtracker.ui.UserProfileScreen.GitHubViewModel
import com.example.githubtracker.ui.UserProfileScreen.UserProfileScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class MainScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testUserProfileScreenDisplaysUserData() {
        composeTestRule.setContent {
            val fakeRepository = NetworkGitHubRepository(FakeGitHubApiService())
            val viewModel = GitHubViewModel(fakeRepository)
            UserProfileScreen(viewModel, Modifier, rememberNavController())
        }

        composeTestRule.onNodeWithText("Test User").assertExists()
        composeTestRule.onNodeWithText("Repo1").assertExists()
    }
}
