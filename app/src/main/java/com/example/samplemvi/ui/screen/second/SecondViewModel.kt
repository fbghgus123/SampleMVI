package com.example.samplemvi.ui.screen.second

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import com.example.samplemvi.base.BaseStateViewModel
import com.example.samplemvi.ui.screen.second.SecondContract.SecondEvent
import com.example.samplemvi.ui.screen.second.SecondContract.SecondReduce
import com.example.samplemvi.ui.screen.second.SecondContract.SecondSideEffect
import com.example.samplemvi.ui.screen.second.SecondContract.SecondState
import javax.inject.Inject

class SecondViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseStateViewModel<SecondState, SecondEvent, SecondReduce, SecondSideEffect>(savedStateHandle) {
    override fun createInitialState(savedState: Parcelable?): SecondState {
        return savedState as? SecondState ?: SecondState()
    }

    override fun handleEvents(event: SecondEvent) {
        when (event) {
            is SecondEvent.OnClickMinusButton -> updateState(SecondReduce.UpdateNumber(currentState.num - 1))
        }
    }

    override fun handleErrors(error: Throwable) {
        when (error) {
            is Error -> {} // 특정 에러 처리
            else -> {} //일반 에러 처리
        }
    }

    override fun reduceState(state: SecondState, reduce: SecondReduce): SecondState =
        when (reduce) {
            is SecondReduce.UpdateNumber -> state.copy(num = reduce.amount)
        }
}