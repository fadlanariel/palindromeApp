package com.fadlan.palindromeapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource.LoadResult.Page.Companion.COUNT_UNDEFINED
import androidx.paging.liveData
import com.fadlan.palindromeapp.data.UserData
import com.fadlan.palindromeapp.data.UserPagingSource
import com.fadlan.palindromeapp.retrofit.ApiService

class UserRepository(private val apiService: ApiService) {

   fun getUsers(): LiveData<PagingData<UserData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 8,
                jumpThreshold = COUNT_UNDEFINED
            ),
            pagingSourceFactory = {
                UserPagingSource(apiService)
            }
        ).liveData
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(apiService: ApiService): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also { instance = it }
    }
}