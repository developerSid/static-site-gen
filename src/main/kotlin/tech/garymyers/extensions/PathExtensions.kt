package tech.garymyers.extensions

import org.apache.commons.io.FilenameUtils
import java.nio.file.Files
import java.nio.file.Path

fun Path.isFileAndExists(): Boolean =
   Files.exists(this) && Files.isRegularFile(this)

/**
 * Find the path leading up to a file and creating a relative path using the provided base
 */
fun Path.relativeToForBase(base: Path): Path {
   val relativePath = this.toString().substringAfter("${base}/")

   return Path.of(FilenameUtils.getPath(relativePath))
}