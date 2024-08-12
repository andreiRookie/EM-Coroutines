package org.example

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

fun main() {
    val flow = (1..10).asFlow()

    runBlocking {
        var start1 = System.currentTimeMillis()
        flow
            .onEach { delay(100) }
            .throttleFirst(450)
            .collect {
                print("${System.currentTimeMillis() - start1} - $it; ")
            }
        println("\n")
        start1 = System.currentTimeMillis()
        flow
            .onEach { delay(100) }
            .throttleLatest(450)
            .collect {
                print("${System.currentTimeMillis() - start1} - $it; ")
            }
    }
}