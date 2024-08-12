package org.example

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() {
    val flow = getFlow()

    runBlocking {
        var start1 = System.currentTimeMillis()
        flow
            .onEach { delay(100) }
            .throttleFirst(200)
            .collect {
                print("${System.currentTimeMillis() - start1} - $it; ")
            }
        println("\n")
        start1 = System.currentTimeMillis()
        flow
            .onEach { delay(100) }
            .throttleLatest(200)
            .collect {
                print("${System.currentTimeMillis() - start1} - $it; ")
            }

    }


}

private fun getFlow(): Flow<Int> = flow {
    list.onEach {
        emit(it)
    }
}

private val list = (1..10).toList()
