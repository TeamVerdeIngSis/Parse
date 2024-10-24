package com.github.teamverdeingsis.parse.languages

import com.github.teamverdeingsis.parse.models.*
import com.github.teamverdeingsis.parse.models.results.ExecutionResult
import com.github.teamverdeingsis.parse.models.results.FormattedSnippet
import com.github.teamverdeingsis.parse.models.results.LintingResult
import com.github.teamverdeingsis.parse.models.results.ValidationResult
import commands.AnalyzingCommand
import commands.ValidationCommand
import commands.FormattingCommand
import commands.ExecutionCommand

class PrintScriptLanguageHandler : LanguageHandler {

    override fun lint(code: String, rules: List<String>): LintingResult {
        // Usa AnalyzingCommand para lintear el código según las reglas de linter
        val analyzer = AnalyzingCommand(code, "linter-config.json")
        val errors = analyzer.run()  // Linting result
        return LintingResult(success = errors.isEmpty(), errors = errors)
    }

    override fun format(code: String, rules: List<String>): FormattedSnippet {
        // Usa FormattingCommand para formatear el código según las reglas
        val formatter = FormattingCommand(code, "format-config.json")
        val formattedCode = formatter.execute() // Returns the formatted code
        return FormattedSnippet(formattedCode = formattedCode)
    }

    override fun validate(code: String): ValidationResult {
        // Usa ValidationCommand para validar y parsear el código
        val validator = ValidationCommand(code)
        val ast = validator.execute()  // AST result
        return ValidationResult(isValid = true, ast = ast)
    }

    override fun execute(code: String): ExecutionResult {
        // Usa ExecutionCommand para lexear, parsear e interpretar el código
        val executor = ExecutionCommand(code)
        val output = executor.execute() // Returns the result of the execution
        return ExecutionResult(output = output)
    }
}
