package com.workspace.core.data.di

import com.workspace.core.data.api.PokeService
import com.workspace.core.data.datasource.PokemonDataSource
import com.workspace.core.data.datasource.PokemonDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providePokemonRemoteDataSource(service: PokeService): PokemonDataSource {
        return PokemonDataSourceImpl(service)
    }
}