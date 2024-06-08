package com.fadlan.palindromeapp.view.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fadlan.palindromeapp.data.UserData
import com.fadlan.palindromeapp.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository): ViewModel() {
    val users: LiveData<PagingData<UserData>> = repository.getUsers().cachedIn(viewModelScope)


}