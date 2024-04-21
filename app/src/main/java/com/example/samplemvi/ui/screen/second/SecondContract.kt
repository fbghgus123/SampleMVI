package com.example.samplemvi.ui.screen.second

import android.os.Parcelable
import com.example.samplemvi.base.ViewModelContract
import kotlinx.parcelize.Parcelize

class SecondContract {
    @Parcelize
    data class SecondState(
        val num: Int = 1000
    ) : ViewModelContract.State, Parcelable

    sealed interface SecondEvent : ViewModelContract.Event {
        data object OnClickMinusButton : SecondEvent
    }

    sealed interface SecondReduce : ViewModelContract.Reduce {
        data class UpdateNumber(val amount: Int) : SecondReduce
    }

    sealed interface SecondSideEffect : ViewModelContract.SideEffect
}