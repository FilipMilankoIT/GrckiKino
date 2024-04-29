package com.mozzartbet.grckikino

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mozzartbet.core.Repository
import com.mozzartbet.core.model.Draw
import com.mozzartbet.core.model.RepositoryResult
import com.mozzartbet.grckikino.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val networkUtils: NetworkUtils
): ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
        private const val NEXT_DRAWS_SIZE = 20
    }

    private val _nextDraws = MutableStateFlow(listOf<Draw>())
    val nextDraws = _nextDraws.asStateFlow()

    private val _selectedDraw = MutableStateFlow<Draw?>(null)
    val selectedDraw = _selectedDraw.asStateFlow()

    private val _drawResults = MutableStateFlow(listOf<Draw>())
    val drawResults = _drawResults.asStateFlow()

    private var _networkConnection = MutableStateFlow(false)
    val networkConnection = _networkConnection.asStateFlow()

    private val _waiting = MutableStateFlow(false)
    val waiting = _waiting.asStateFlow()

    private val _message = MutableSharedFlow<Int>()
    val message = _message.asSharedFlow()

    private var fetchingNextDraws = false

    init {
        networkUtils.setConnectionListener(object : NetworkUtils.ConnectionListener {
            override fun isConnected(value: Boolean) {
                viewModelScope.launch {
                    withContext(Dispatchers.Main) {
                        Log.i(TAG, "Network connection: $value")
                        _networkConnection.value = value
                    }
                }
            }
        })
    }

    override fun onCleared() {
        super.onCleared()

        networkUtils.removeConnectionListener()
    }

    fun fetchNextDraw() {
        if (fetchingNextDraws) return
        viewModelScope.launch {
            _waiting.value = true
            fetchingNextDraws = true
            val result = repository.getUpcomingKinoDraws(NEXT_DRAWS_SIZE)
            _waiting.value = false
            fetchingNextDraws = false
            when(result) {
                is RepositoryResult.Success -> {
                    _nextDraws.value = result.data.filter {
                        it.drawTime > Date().time
                    }
                }
                is RepositoryResult.UnauthorisedError -> {
                    _message.emit(R.string.unauthorised_error)
                }
                is RepositoryResult.NetworkError -> _message.emit(R.string.network_error)
                else -> _message.emit(R.string.fetch_next_draws_error)
            }
        }
    }

    fun fetchSelectedDraw(drawID: Int) {
        viewModelScope.launch {
            _selectedDraw.value = null
            _waiting.value = true
            val result = repository.getKinoDraw(drawID)
            _waiting.value = false
            when(result) {
                is RepositoryResult.Success -> {
                    _selectedDraw.value = result.data
                }
                is RepositoryResult.UnauthorisedError -> {
                    _message.emit(R.string.unauthorised_error)
                }
                is RepositoryResult.NetworkError -> {
                    _message.emit(R.string.network_error)
                }
                else -> {
                    _message.emit(R.string.fetch_draw_error)
                }
            }
        }
    }

    fun fetchDrawResults(fromDate: String, toDate: String) {
        viewModelScope.launch {
            _drawResults.value = listOf()
            _waiting.value = true
            val result = repository.getKinoDrawResults(fromDate, toDate)
            _waiting.value = false
            fetchingNextDraws = false
            when(result) {
                is RepositoryResult.Success -> {
                    _drawResults.value = result.data
                }
                is RepositoryResult.UnauthorisedError -> {
                    _message.emit(R.string.unauthorised_error)
                }
                is RepositoryResult.NetworkError -> _message.emit(R.string.network_error)
                else -> _message.emit(R.string.fetch_next_draws_error)
            }
        }
    }
}