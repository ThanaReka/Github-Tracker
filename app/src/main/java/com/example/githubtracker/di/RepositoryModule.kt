package com.example.githubtracker.di

import com.example.githubtracker.data.GitHubRepository
import com.example.githubtracker.data.NetworkGitHubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindNewsRepository(networkGitHubRepository: NetworkGitHubRepository): GitHubRepository
}