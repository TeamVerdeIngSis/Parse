package com.github.teamverdeingsis.parse.factory

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class RulesFactory {
    companion object {
        fun defaultFormattingRules(): JsonNode {
            return jacksonObjectMapper().readTree(
                """
{
    "space-before-colon": false,
    "space-after-colon": false,
    "space-around-equals": false,
    "newline-before-println": 0,
    "indentation": 4
}
""",
            )
        }
    }
}
