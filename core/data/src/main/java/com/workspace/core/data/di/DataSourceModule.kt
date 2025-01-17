package com.workspace.core.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.workspace.core.data.api.PokeService
import com.workspace.core.data.datasource.AuthDataSource
import com.workspace.core.data.datasource.AuthDataSourceImpl
import com.workspace.core.data.datasource.FirestoreSource
import com.workspace.core.data.datasource.FirestoreSourceImpl
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

    @Provides
    @Singleton
    fun provideAuthDataSource(firebaseAuth: FirebaseAuth): AuthDataSource {
        return AuthDataSourceImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideFirestoreSource(firestore: FirebaseFirestore): FirestoreSource {
        return FirestoreSourceImpl(firestore)
    }
}