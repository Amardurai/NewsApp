package com.example.androidcleanarchitecture.presentation.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.example.androidcleanarchitecture.data.model.request.RequestException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
) : LifecycleObserver, ViewModel() {

    private val _isNetworkAvailable: MutableState<Boolean> = mutableStateOf(false)
    val isNetworkAvailable: State<Boolean> = _isNetworkAvailable
    private val _unauthorized: MutableLiveData<Boolean> = MutableLiveData()

    val loading: LiveData<Boolean>
        get() = _loading
            .distinctUntilChanged()
    private val _loading: MutableLiveData<Boolean> = MutableLiveData()

    private val _error: LiveEvent<String> = LiveEvent()
    val error: LiveEvent<String> get() = _error

    protected fun <T> doAnAPICall(
        apiCall: suspend () -> Result<T>,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        handleLoading: Boolean = true,
        handleError: Boolean = true
    ) = viewModelScope.launch {
        val result = apiCall.invoke()

        if (handleLoading) {
            _loading.postValue(true)
        }
        if (handleLoading) {
            _loading.postValue(false)
        }

        // Check for result
        result.getOrNull()?.let { value ->
            onSuccess?.invoke(value)
        }
        result.exceptionOrNull()?.let { error ->
            onError?.invoke(error)
            if (handleError) {
                onCallError(error)
            }
        }

    }

    protected fun onCallError(error: Throwable) {
        checkIsUnauthorized(error)
        setError(error.message.orEmpty())
    }

    protected fun setLoading(isLoading: Boolean) = _loading.postValue(isLoading)

    protected fun setError(errorMessage: String) = _error.postValue(errorMessage)

    private fun checkIsUnauthorized(error: Throwable) {
        if (error is RequestException && error.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            _unauthorized.postValue(true)
        }
    }

    /*private suspend fun testNetwork() {
        val isNotConnected = application.isNetworkAvailable().not()
        _isNetworkAvailable.value = isNotConnected
        if (isNotConnected) {
            delay(timeMillis = 2000)
            testNetwork()
        }
    }*/


}

fun Context.isNetworkAvailable(): Boolean {
    var result = false
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    cm?.run {
        cm.getNetworkCapabilities(cm.activeNetwork)?.run {
            result = when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
    }
    return result
}

