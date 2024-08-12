package org.example

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit


fun <T> Flow<T>.throttleFirst(
    intervalDuration: Long,
    unit: TimeUnit = TimeUnit.MILLISECONDS,
): Flow<T> {
    var wasEmitted = false
    var currentTime = 0L
    var diff = 0L
    var currentIntervalTime = System.currentTimeMillis()

    return flow {
        collect { value ->

            currentTime = System.currentTimeMillis()
            diff = currentTime - currentIntervalTime

            if (diff >= intervalDuration) {
                currentIntervalTime += diff
                wasEmitted = false
            }

            if (!wasEmitted) {
                emit(value)
                wasEmitted = true
            }
        }
    }
}

fun <T> Flow<T>.throttleLatest(
    intervalDuration: Long,
    unit: TimeUnit = TimeUnit.MILLISECONDS,
): Flow<T> {
    var lastValue: Any? = null
    var currentTime = 0L
    var diff = 0L
    var wasEmitted = false
    var isFirstValue = true
    var currentIntervalTime = System.currentTimeMillis()


    return flow {
        collect { value ->

//            if (isFirstValue) {
//                lastValue = value
//                lastValue?.let { emit(lastValue as T) }
//                isFirstValue = false
//
//                currentTime = System.currentTimeMillis()
//                diff = currentTime - currentIntervalTime
//                currentIntervalTime += diff
//            } else {

                currentTime = System.currentTimeMillis()
                diff = currentTime - currentIntervalTime

                if (diff < intervalDuration) {
                    lastValue = value
                } else {
                    currentIntervalTime += diff
                    wasEmitted = false
                }

                if (!wasEmitted) {
                    lastValue?.let { emit(lastValue as T) }
                    wasEmitted = true
                }
            }
//        }
    }
}
