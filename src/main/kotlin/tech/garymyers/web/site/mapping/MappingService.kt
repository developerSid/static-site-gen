package tech.garymyers.web.site.mapping

import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import javax.inject.Singleton

@Singleton
class MappingService {

   fun map(htDocs: Path): SiteMapping {
      Files.walkFileTree()
      TODO("Not Implemented")
   }
}

private class HtDocsVisitor: SimpleFileVisitor<Path>() {
   override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {

   }
}