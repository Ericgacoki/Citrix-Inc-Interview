package com.ericdev.citrixincinterview.di

import com.ericdev.citrixincinterview.data.repository.MainDataRepository
import com.ericdev.citrixincinterview.domain.repository.MainRepository
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
    abstract fun bindDataRepository(mainRepository: MainDataRepository): MainRepository
}
