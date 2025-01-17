package com.workspace.core.data.di

import com.workspace.core.data.repository.AuthRepositoryImpl
import com.workspace.core.data.repository.PokemonRepositoryImpl
import com.workspace.core.data.repository.UserRepositoryImpl
import com.workspace.core.domain.repository.AuthRepository
import com.workspace.core.domain.repository.PokemonRepository
import com.workspace.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPokemonRepository(
        pokemonRepositoryImpl: PokemonRepositoryImpl
    ): PokemonRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}