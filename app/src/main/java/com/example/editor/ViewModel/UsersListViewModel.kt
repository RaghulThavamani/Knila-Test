package com.example.editor.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.dreamBig.bookapp.repository.Repository
import com.example.editor.model.User

class UsersListViewModel : ViewModel() {
    private val _usersData = MutableLiveData<User>()
    val users: LiveData<User> = _usersData

    suspend fun getUsers() {
        _usersData.postValue(Repository.getUsersList())
    }
}