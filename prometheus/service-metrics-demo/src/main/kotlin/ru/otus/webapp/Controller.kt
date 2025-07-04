package ru.otus.webapp

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class Controller {
    private val random = Random(42)

    private fun doSomething(res: String, from: Int, to: Int): String {
        val timeout = random.nextLong(from.toLong(), to.toLong())
        Thread.sleep(timeout)
        val error = random.nextInt(10)
        if (error > 8) throw RuntimeException("Error $error")
        return "$res: $timeout"
    }

    @GetMapping("/api/a")
    fun method1() = doSomething("method1", 0, 300)

    @GetMapping("/api/b")
    fun method2() = doSomething("method2", 200, 400)
}