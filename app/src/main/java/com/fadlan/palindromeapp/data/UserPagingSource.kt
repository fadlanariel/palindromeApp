package com.fadlan.palindromeapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fadlan.palindromeapp.retrofit.ApiService

class UserPagingSource(private val apiService: ApiService) : PagingSource<Int, UserData>() {
    override fun getRefreshKey(state: PagingState<Int, UserData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserData> {
        return try {
            val pageNumber = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getUsers(pageNumber, PAGE_SIZE).data

            val prevKey = if (pageNumber == INITIAL_PAGE_INDEX) null else pageNumber - 1
            val nextKey = if (responseData.isNullOrEmpty() || responseData.size < PAGE_SIZE) null else pageNumber + 1

            LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
        const val PAGE_SIZE = 8
    }
}