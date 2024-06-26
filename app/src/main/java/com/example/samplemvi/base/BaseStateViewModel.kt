package com.example.samplemvi.base

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseStateViewModel<
        S : ViewModelContract.State,
        E : ViewModelContract.Event,
        R : ViewModelContract.Reduce,
        SE : ViewModelContract.SideEffect
        >(
    private val stateHandler: SavedStateHandle
) : ViewModel() {
    protected abstract fun createInitialState(savedState: Parcelable?): S

    private val initialState: S by lazy {
        createInitialState(stateHandler[STATE_KEY])
    }

    private val _viewState = MutableStateFlow(initialState)
    val viewState: StateFlow<S> = _viewState.asStateFlow()
    protected val currentState: S get() = _viewState.value

    private val _events = MutableSharedFlow<E>()
    private val _reduce = MutableSharedFlow<R>()

    private val _sideEffect: Channel<SE> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _error: Channel<Throwable> = Channel()

    private val _isLoading: Channel<Boolean> = Channel()
    val isLoading = _isLoading.receiveAsFlow()
    fun setLoading(isLoading: Boolean) {
        viewModelScope.launch {
            _isLoading.send(isLoading)
        }
    }

    init {
        _events.onEach(::handleEvents)
            .launchIn(viewModelScope)
        _reduce.onEach {
            _viewState.value = reduceState(currentState, it).also { state ->
                stateHandler[STATE_KEY] = state.toParcelable()
            }
        }.launchIn(viewModelScope)
        _error.receiveAsFlow()
            .onEach(::handleErrors)
            .launchIn(viewModelScope)
    }

    val ceh = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        throwError(throwable)
        setLoading(false)
    }

    inline fun launch(
        showLoading: Boolean = true,
        crossinline action: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(ceh) {
            if (showLoading) setLoading(true)
            action(this)
            if (showLoading) setLoading(false)
        }
    }

    protected fun updateState(reduce: R) {
        viewModelScope.launch {
            _reduce.emit(reduce)
        }
    }

    protected fun sendSideEffect(effect: SE) {
        viewModelScope.launch {
            _sideEffect.send(effect)
        }
    }

    fun sendEvent(event: E) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    private fun throwError(error: Throwable) {
        viewModelScope.launch {
            _error.send(error)
        }
    }

    abstract fun handleEvents(event: E)

    abstract fun reduceState(state: S, reduce: R): S

    abstract fun handleErrors(error: Throwable)

    companion object {
        const val STATE_KEY = "viewState"
    }
}