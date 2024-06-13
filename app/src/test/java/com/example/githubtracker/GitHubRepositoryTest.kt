package com.example.githubtracker

import com.example.githubtracker.data.NetworkGitHubRepository
import com.example.githubtracker.fake.FakeGitHubApiService
import com.example.githubtracker.rules.TestDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class GitHubRepositoryTest {

    private val fakeApiService = FakeGitHubApiService()
    private val repository = NetworkGitHubRepository(fakeApiService)

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun testGetUser() = runTest {
        val user = repository.getUser("testuser")
        assertEquals("Test User", user.name)
    }

    @Test
    fun testGetUserRepos() = runTest {
        val repos = repository.getUserRepos("testuser")
        assertEquals(2, repos.size)
        assertEquals("Repo1", repos[0].name)
    }
}
