package tech.garymyers.web.site.mapping

import org.apache.commons.configuration2.Configuration
import org.apache.commons.configuration2.FileBasedConfiguration
import org.apache.commons.configuration2.PropertiesConfiguration
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder
import org.apache.commons.configuration2.builder.fluent.Parameters
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tech.garymyers.extensions.isFileAndExists
import tech.garymyers.web.site.mapping.content.ContentListing
import tech.garymyers.web.site.mapping.theme.ThemeListing
import java.nio.file.FileSystems
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitResult.CONTINUE
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.PathMatcher
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import java.util.Deque
import javax.inject.Singleton

private enum class LoadingMode {
   CONTENT,
   THEME,
}

@Singleton
class MappingService {
   private val logger: Logger = LoggerFactory.getLogger(MappingService::class.java)
   private val jenniePropertiesPathMatcher: PathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/jennie.properties")

   fun map(htDocs: Path): SiteMapping {
      val absoluteHtDocs = htDocs.toAbsolutePath().normalize()
      logger.info("Rendering from {}", absoluteHtDocs)

      val configuration = loadConfiguration(absoluteHtDocs.resolve("jennie.properties"))
      var loadingMode = ArrayDeque<LoadingMode>()

      Files.walkFileTree(absoluteHtDocs, object : SimpleFileVisitor<Path>() {
         override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
            if (!jenniePropertiesPathMatcher.matches(file)) { // only load the root jennie.properties configuration file
               
            }

            return CONTINUE
         }
      })

      return SiteMapping(
         theme = ThemeListing(),
         content = ContentListing(),
         configuration = configuration,
      )
   }

   private fun loadConfiguration(propertiesPath: Path): Configuration {
      return if (propertiesPath.isFileAndExists()) {
         logger.debug("Loading {} for configuration", propertiesPath)

         FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration::class.java)
            .configure(Parameters().properties().setFile(propertiesPath.toFile()))
            .configuration
      } else {
         logger.debug("Using default configuration")

         PropertiesConfiguration() // default
      }
   }
}
