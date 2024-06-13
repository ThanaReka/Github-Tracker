package com.example.githubtracker.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    @SerialName("avatar_url")
    val avatarUrl: String
)
@Serializable
data class Repo(
    val name: String,
    val description: String?,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    val forks: Int
)

