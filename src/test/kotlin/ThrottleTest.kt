import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.example.throttleFirst
import kotlin.test.Test
import kotlin.test.assertEquals


class ThrottleTest {

    @Test
    fun throttleFirst_test_interval_200() {
        runBlocking {

            val flow = (1..10).asFlow()

            val actual = flow
                .onEach { delay(100) }
                .throttleFirst(200)
                .toList()

            val expected = listOf(1,2,4,6,8,10)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun throttleFirst_interval_300() {
        runBlocking {

            val flow = (1..10).asFlow()

            val actual = flow
                .onEach { delay(100) }
                .throttleFirst(300)
                .toList()

            val expected = listOf(1,3,6,9)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun throttleFirst_interval_400() {
        runBlocking {

            val flow = (1..10).asFlow()

            val actual = flow
                .onEach { delay(100) }
                .throttleFirst(400)
                .toList()

            val expected = listOf(1,4,8)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun throttleFirst_interval_500() {
        runBlocking {

            val flow = (1..10).asFlow()

            val actual = flow
                .onEach { delay(100) }
                .throttleFirst(500)
                .toList()

            val expected = listOf(1,5,10)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun throttleFirst_interval_600() {
        runBlocking {

            val flow = (1..10).asFlow()

            val actual = flow
                .onEach { delay(100) }
                .throttleFirst(600)
                .toList()

            val expected = listOf(1,6)
            assertEquals(expected, actual)
        }
    }
}