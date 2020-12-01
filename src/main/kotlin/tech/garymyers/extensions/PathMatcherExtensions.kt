package tech.garymyers.extensions

import java.nio.file.Path
import java.nio.file.PathMatcher

fun PathMatcher.doesNotMatch(path: Path): Boolean =
   !this.matches(path)