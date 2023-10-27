package fr.eurosport.sportstories.common.util

import androidx.annotation.StringRes

sealed interface UIEvent {
    data class ShowError(
        val errorType: ErrorType? = null,
        @StringRes val errorStrRes: Int
    ) : UIEvent
}
