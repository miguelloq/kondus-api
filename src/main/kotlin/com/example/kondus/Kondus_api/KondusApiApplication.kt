package com.example.kondus.Kondus_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class KondusApiApplication

fun main(args: Array<String>) {
    runApplication<KondusApiApplication>(*args)
}

@RestController
class HelloController(private val service: HelloService) {

    @GetMapping("/")
    fun hello() = service.hello()
}

@Service
class HelloService {
    fun hello() = "Hello"
}
