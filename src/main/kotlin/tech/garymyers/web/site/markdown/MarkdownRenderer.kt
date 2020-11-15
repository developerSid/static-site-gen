package tech.garymyers.web.site.markdown

import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import java.io.File
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.Path
import javax.inject.Singleton

@Singleton
class MarkdownRenderer {
   fun render(htDocsPath: Path) {
      val parser = Parser.builder().build()
      val htmlRenderer = HtmlRenderer.builder().build()
      val root = htDocsPath.resolve("site").resolve("root.md")

      Files.newBufferedReader(root).use { rootReader ->
         val node = parser.parseReader(rootReader)

         FileWriter(File("/tmp/root.html")).use { fileWriter ->
            htmlRenderer.render(node, fileWriter)

            fileWriter.flush()
         }
      }
   }
}