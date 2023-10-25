package fr.eurosport.sportstories.common.util


sealed class Resource<T>(
    val data: T? = null,
    val dataError: DataError = DataError.NONE
) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(dataError: DataError, data: T? = null) : Resource<T>(data, dataError)
}