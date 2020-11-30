package tech.garymyers.extensions

import java.nio.file.Files
import java.nio.file.Path

fun Path.isFileAndExists(): Boolean =
   Files.exists(this) && Files.isRegularFile(this)