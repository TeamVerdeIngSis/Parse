package com.example.parser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration

@SpringBootApplication
class ParseApplication

fun main(args: Array<String>) {
	runApplication<ParseApplication>(*args)
}
