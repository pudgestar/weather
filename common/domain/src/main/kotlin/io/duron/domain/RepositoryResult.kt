package io.duron.domain

sealed class RepositoryResult <T> {
    data class Success<T>(val response: T): RepositoryResult<T>()
    data class Error<T>(val throwable: Throwable): RepositoryResult<T>()
}