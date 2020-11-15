package tech.garymyers.web;

enum class ExitStatus(
   val exitCode: Int
) {
   HT_DOCS_NOT_FOUND(2),
   SUCCESS(0),
}