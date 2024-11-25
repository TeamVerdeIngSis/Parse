package com.github.teamverdeingsis.parse.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class SnippetMessage(
    val snippetId: String,
    val userId: String,
)

data class Rule(
    val id: String,
    val name: String,
    val isActive: Boolean,
    val value: String? = null
)


enum class Conformance {
    PENDING,
    COMPLIANT,
    NOT_COMPLIANT
}

data class UpdateConformanceRequest(
    val snippetId: String,
    val conformance: Conformance
)


data class LintingConfig(
    @JsonProperty("identifier_format")
    var identifierFormat: String = "none", // Default to none
    @JsonProperty("mandatory-variable-or-literal-in-println")
    var mandatoryVariableOrLiteral: String = "none",
    @JsonProperty("read-input-with-simple-argument")
    var readInputWithSimpleArgument: String = "none",
)

data class FormattingConfig(
    @JsonProperty("space-before-colon")
    var spaceBeforeColon: Boolean = false,
    @JsonProperty("space-after-colon")
    var spaceAfterColon: Boolean = false,
    @JsonProperty("space-around-equals")
    var spaceAroundEquals: Boolean = false,
    @JsonProperty("newline-before-println")
    var newlineBeforePrintln: Int = 0,
    @JsonProperty("indentation")
    var indentation: Int = 4,
)
