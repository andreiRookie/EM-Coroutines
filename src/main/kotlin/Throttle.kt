package org.example

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


fun <T> Flow<T>.throttleFirst(
    intervalDurationMillis: Long,
): Flow<T> = flow {
    var wasEmitted = false
    var diff: Long
    var currentIntervalTime = System.currentTimeMillis()

    collect { value ->

        diff = System.currentTimeMillis() - currentIntervalTime

        if (diff >= intervalDurationMillis) {
            currentIntervalTime += diff
            wasEmitted = false
        }

        if (!wasEmitted) {
            emit(value)
            wasEmitted = true
        }
    }
}

fun <T> Flow<T>.throttleLatest(
    intervalDurationMillis: Long,
): Flow<T> = flow {

    var lastValue: Any?
    var diff: Long
    var wasEmitted = false
    var currentIntervalTime = System.currentTimeMillis()

    collect { value ->

        diff = System.currentTimeMillis() - currentIntervalTime

        if (diff < intervalDurationMillis) {
            lastValue = value
        } else {
            currentIntervalTime += diff

            lastValue = value
            wasEmitted = false
        }

        if (!wasEmitted) {
            lastValue?.let { emit(lastValue as T) }
            wasEmitted = true
        }
    }
}

