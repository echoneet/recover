package dev.echoneet.recover.data.model

data class ResultWithStatus<out T>(
    val status: Status,
    val data: T?,
    val error: Exception?,
    val message: String?
) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): ResultWithStatus<T> {
            return ResultWithStatus(Status.SUCCESS, data, null, null)
        }

        fun <T> error(data: T? = null, message: String?, error: Exception?): ResultWithStatus<T> {
            return ResultWithStatus(Status.ERROR, data, error, message)
        }

        fun <T> loading(data: T? = null): ResultWithStatus<T> {
            return ResultWithStatus(Status.LOADING, data, null, null)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, data=$data, error=$error, message=$message)"
    }
}