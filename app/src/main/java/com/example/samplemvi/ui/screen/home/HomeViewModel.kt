package com.example.samplemvi.ui.screen.home

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import com.example.samplemvi.base.BaseStateViewModel
import com.example.samplemvi.ui.screen.home.HomeContract.HomeEvent
import com.example.samplemvi.ui.screen.home.HomeContract.HomeReduce
import com.example.samplemvi.ui.screen.home.HomeContract.HomeSideEffect
import com.example.samplemvi.ui.screen.home.HomeContract.HomeState
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseStateViewModel<HomeState, HomeEvent, HomeReduce, HomeSideEffect>(savedStateHandle) {

    override fun createInitialState(savedState: Parcelable?): HomeState {
        return savedState as? HomeState ?: HomeState()
    }

    override fun handleEvents(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnClickNavigateButton -> sendSideEffect(HomeSideEffect.NavigateToSecondPage)
            is HomeEvent.OnClickPlusButton -> updateState(HomeReduce.UpdateNumber(currentState.num + 1))
        }
    }

    override fun handleErrors(error: Throwable) {
        when (error) {
            is Error -> {} // 특정 에러 처리
            else -> {} //일반 에러 처리
        }
    }

    override fun reduceState(state: HomeState, reduce: HomeReduce): HomeState =
        when (reduce) {
            is HomeReduce.UpdateNumber -> state.copy(num = reduce.amount)
        }

}