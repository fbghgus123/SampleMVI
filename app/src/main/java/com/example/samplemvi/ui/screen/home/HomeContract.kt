package com.example.samplemvi.ui.screen.home

import android.os.Parcelable
import com.example.samplemvi.base.ViewModelContract
import kotlinx.parcelize.Parcelize

class HomeContract {
    @Parcelize
    data class HomeState(
        val num: Int = 0
    ) : ViewModelContract.State, Parcelable

    sealed interface HomeEvent : ViewModelContract.Event {
        data object OnClickNavigateButton : HomeEvent
        data object OnClickPlusButton : HomeEvent
    }

    sealed interface HomeReduce : ViewModelContract.Reduce {
        data class UpdateNumber(val amount: Int) : HomeReduce
    }

    sealed interface HomeSideEffect : ViewModelContract.SideEffect {
        data object NavigateToSecondPage : HomeSideEffect
    }
}