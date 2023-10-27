package fr.eurosport.sportstories.common.util


sealed class Resource<T>(
    val data: T? = null,
    val dataError: ErrorType = ErrorType.NONE
) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(dataError: ErrorType, data: T? = null) : Resource<T>(data, dataError)
}