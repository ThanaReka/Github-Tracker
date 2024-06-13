package com.example.githubtracker

import com.example.githubtracker.model.Repo
import com.example.githubtracker.model.User
import com.example.githubtracker.network.GitHubApiService

class FakeGitHubApiService : GitHubApiService {
    override suspend fun getUser(userId: String): User {
        return User(

            name = "Test User",
            avatar_url = "https://example.com/avatar.png"
        )
    }

    override suspend fun getUserRepos(userId: String): List<Repo> {
        return listOf(
            Repo(name = "Repo1", description = "Test Repo 1", stargazers_count = 10, forks = 5, updated_at = "2023-01-01"),
            Repo(name = "Repo2", description = "Test Repo 2", stargazers_count = 20, forks = 15, updated_at = "2023-01-02")
        )
    }
}
