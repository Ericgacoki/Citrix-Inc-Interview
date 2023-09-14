package com.ericdev.citrixincinterview.di

import android.app.Application
import androidx.room.Room
import com.ericdev.citrixincinterview.data.local.database.CitrixAppDatabase
import com.ericdev.citrixincinterview.data.remote.api.ApiService
import com.ericdev.citrixincinterview.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    /** Set a 15 sec timeout duration for each API call.
     *
     * An exception is thrown if no response has been received from the server within this period.
     * */
    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(httpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesAppDatabase(application: Application): CitrixAppDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            CitrixAppDatabase::class.java,
            "citrix_app_database"
        ).fallbackToDestructiveMigration().build()
    }
}
