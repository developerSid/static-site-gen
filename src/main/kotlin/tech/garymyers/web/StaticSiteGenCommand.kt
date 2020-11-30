package tech.garymyers.web

import io.micronaut.configuration.picocli.PicocliRunner
import io.micronaut.core.annotation.ReflectiveAccess
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import tech.garymyers.web.ExitStatus.HT_DOCS_NOT_FOUND
import tech.garymyers.web.ExitStatus.SUCCESS
import tech.garymyers.web.site.mapping.MappingService
import tech.garymyers.web.site.markdown.MarkdownRenderer
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.Callable
import javax.inject.Inject
import kotlin.system.exitProcess

@Command(
   name = "ssg",
   description = ["Static site generator"],
   mixinStandardHelpOptions = true
)
class StaticSiteGenCommand : Callable<ExitStatus> {

   @Parameters(index = "0", description = ["Location of markdown and template files", "to be used to generate static html/css"], defaultValue = "./")
   private var htdocs: String = "./"

   @ReflectiveAccess @Inject private lateinit var markdownRenderer: MarkdownRenderer
   @ReflectiveAccess @Inject private lateinit var mappingService: MappingService

   override fun call(): ExitStatus {
      val htDocsPath = Path.of(htdocs)

      return if (Files.exists(htDocsPath) && Files.isDirectory(htDocsPath)) {
         mappingService.map(htDocsPath)
         SUCCESS
      } else {
         HT_DOCS_NOT_FOUND
      }
   }

   companion object {

      @JvmStatic
      fun main(args: Array<String>) {
         val status: ExitStatus? = PicocliRunner.call(StaticSiteGenCommand::class.java, *args)

         exitProcess(status?.exitCode ?: SUCCESS.exitCode)
      }
   }
}
