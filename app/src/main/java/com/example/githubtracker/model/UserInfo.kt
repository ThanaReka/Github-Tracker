package com.example.githubtracker.model

import kotlinx.serialization.SerialName

data class User(
    val name: String,
    @SerialName("avatar_url")
    val avatar_url: String
//    val avatarUrl: String
)

data class Repo(
    val name: String,
    val description: String?,
//    @SerialName("updated_at")
//    val updatedAt: String,
    val updated_at: String,
//    @SerialName("stargazers_count")
//    val stargazersCount: Int,
    val stargazers_count: Int,
    val forks: Int
)

