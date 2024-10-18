package com.github.teamverdeingsis.parse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class, HibernateJpaAutoConfiguration::class])
class ParseApplication

fun main(args: Array<String>) {
	runApplication<ParseApplication>(*args)
}
