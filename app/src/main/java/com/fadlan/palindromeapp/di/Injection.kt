package com.fadlan.palindromeapp.di

import android.content.Context
import com.fadlan.palindromeapp.repository.UserRepository
import com.fadlan.palindromeapp.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }
}