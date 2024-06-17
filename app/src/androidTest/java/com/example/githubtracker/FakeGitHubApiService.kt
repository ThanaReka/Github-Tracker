package com.example.githubtracker

import com.example.githubtracker.model.Repo
import com.example.githubtracker.model.User
import com.example.githubtracker.network.GitHubApiService

class FakeGitHubApiService : GitHubApiService {
    override suspend fun getUser(userId: String): User {
        return User(

            name = "Test User",
            avatarUrl = "https://example.com/avatar.png"
        )
    }

    override suspend fun getUserRepos(userId: String): List<Repo> {
        return listOf(
            Repo(name = "Repo1", description = "Test Repo 1", stargazersCount = 10, forks = 5, updatedAt = "2023-01-01"),
            Repo(name = "Repo2", description = "Test Repo 2", stargazersCount = 20, forks = 15, updatedAt = "2023-01-02")
        )
    }
}
