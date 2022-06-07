package com.samuelav.common.utils

sealed class Result<out F, out S> {
    object Loading: Result<Nothing, Nothing>()
    data class Success<S>(val data: S): Result<Nothing, S>()
    data class Failure<F>(val error: F): Result<F, Nothing>()
}

inline fun <reified F, reified S> Result<F, S>.fold(
    isLoading: (() -> Unit) = {},
    isNotLoading: (() -> Unit) = {},
    isSuccess: (S) -> Unit,
    isFailure: (F) -> Unit
) {
    if (this !is Result.Loading) isNotLoading()
    when (this) {
        is Result.Loading -> isLoading()
        is Result.Success -> isSuccess(data)
        is Result.Failure -> isFailure(error)
    }
}

inline fun <reified F, reified S, reified S2> Result<F, S>.mapSuccess(
    map: (S) -> S2
): Result<F, S2> =
    when (this) {
        is Result.Loading -> Result.Loading
        is Result.Success -> Result.Success(map(data))
        is Result.Failure -> Result.Failure(error)
    }

inline val <F, S> Result<F, S>?.isSuccess: Boolean
    get() = this is Result.Success<S>

inline fun <F, S> Result<F, S>.ifSuccess(block: (S) -> Unit): Result<F, S> {
    if (this is Result.Success) block(this.data)

    return this
}