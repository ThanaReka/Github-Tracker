package com.example.githubtracker.network

import com.example.githubtracker.model.Repo
import com.example.githubtracker.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): User

    @GET("users/{userId}/repos")
    suspend fun getUserRepos(@Path("userId") userId: String): List<Repo>
}

