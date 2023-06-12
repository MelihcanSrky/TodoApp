package com.melihcan.todoapp.di

import android.app.Application
import android.content.Context
import com.melihcan.todoapp.service.ServiceInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitClient {

    private const val BASE_URL = "http://192.168.1.87:5000/api/"

    @Singleton
    @Provides
    fun getRetrofitServiceInstance(retrofit: Retrofit): ServiceInstance {
        return retrofit.create(ServiceInstance::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext
}