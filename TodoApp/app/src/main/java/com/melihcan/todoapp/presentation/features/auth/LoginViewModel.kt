package com.melihcan.todoapp.presentation.features.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melihcan.todoapp.model.ErrorModel
import com.melihcan.todoapp.model.LoginResponseModel
import com.melihcan.todoapp.service.repository.AuthRepository
import com.melihcan.todoapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repository: AuthRepository
) : ViewModel() {
    private val _loginResponseData: MutableLiveData<LoginResponseModel> = MutableLiveData()
    val loginResponseData: LiveData<LoginResponseModel> = _loginResponseData

    private val _errorLiveData: MutableLiveData<ErrorModel> = MutableLiveData()
    val errorLiveData: LiveData<ErrorModel> = _errorLiveData

    private val _isSuccessLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessLoading: LiveData<Boolean> = _isSuccessLoading

    init {
        collectFromLoginFlowData()
    }
    fun collectFromLoginFlowData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.loginFlowData.collect {res ->
                when(res) {
                    is Resource.Data -> {
                        _loginResponseData.postValue(res.data)
                        _isSuccessLoading.postValue(true)
                    }
                    is Resource.Error -> {
                        _errorLiveData.postValue(res.error)
                        _isSuccessLoading.postValue(false)
                    }
                }
            }
        }
    }
    fun loginUser(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.loginUser(username, password)
        }
    }
}