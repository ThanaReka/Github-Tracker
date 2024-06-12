package com.example.githubtracker.data

import com.example.githubtracker.model.Repo
import com.example.githubtracker.model.User
import com.example.githubtracker.network.GitHubApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

interface GitHubRepository {
    suspend fun getUser(userId: String): User
    suspend fun getUserRepos(userId: String): List<Repo>

}

class NetworkGitHubRepository @Inject constructor() : GitHubRepository{

//@Singleton
//class GitHubRepository @Inject constructor() {

    private val api: GitHubApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(GitHubApiService::class.java)
    }

    override suspend fun getUser(userId: String): User {
        return api.getUser(userId)
    }

    override suspend fun getUserRepos(userId: String): List<Repo> {
        return api.getUserRepos(userId)
    }
}





