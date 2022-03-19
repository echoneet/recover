package dev.echoneet.recover.presentation.model

enum class ViewStatus {
    FINE,
    ERROR
}

data class ViewState(
    val status: ViewStatus = ViewStatus.FINE,
    val errorMessage: String
)