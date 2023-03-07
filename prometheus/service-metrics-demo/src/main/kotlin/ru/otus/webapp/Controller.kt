package ru.otus.webapp

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class Controller {
    private val random = Random(42)

    private fun doSomething(res: String): String {
        Thread.sleep(random.nextLong(0, 300))
        return res;
    }

    @GetMapping("/api/a")
    fun method1() = doSomething("method1");

    @GetMapping("/api/b")
    fun method2() = doSomething("method2");
}