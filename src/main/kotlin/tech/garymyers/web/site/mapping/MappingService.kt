package tech.garymyers.web.site.mapping

import org.apache.commons.io.input.AutoCloseInputStream
import org.eclipse.collections.api.factory.Maps
import org.eclipse.collections.api.map.ImmutableMap
import org.eclipse.collections.api.map.MutableMap
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tech.garymyers.extensions.doesNotMatch
import tech.garymyers.extensions.isFileAndExists
import tech.garymyers.extensions.relativeToForBase
import tech.garymyers.web.site.mapping.LoadingMode.CONTENT
import tech.garymyers.web.site.mapping.LoadingMode.THEME
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
import java.util.Properties
import javax.inject.Singleton

private enum class LoadingMode {
   CONTENT,
   THEME,
}

@Singleton
class MappingService {
   private val logger: Logger = LoggerFactory.getLogger(MappingService::class.java)
   private val fileSystem = FileSystems.getDefault()
   private val jenniePropertiesPathMatcher: PathMatcher = fileSystem.getPathMatcher("glob:**/jennie.properties")
   private val contentRootPathMatcher: PathMatcher = fileSystem.getPathMatcher("glob:**/content")
   private val themeRootPathMatcher: PathMatcher = fileSystem.getPathMatcher("glob:**/theme")

   fun map(htDocs: Path): SiteMapping {
      val absoluteHtDocs = htDocs.toAbsolutePath().normalize()
      logger.info("Rendering from {}", absoluteHtDocs)

      val expectedContentDir = absoluteHtDocs.resolve("./content/").toAbsolutePath().normalize()
      val expectedThemeDir = absoluteHtDocs.resolve("./theme/").toAbsolutePath().normalize()
      val configuration = loadConfiguration(absoluteHtDocs.resolve("jennie.properties"))
      val contentListing = Maps.mutable.empty<String, Path>()
      val themeListing = Maps.mutable.empty<String, Path>()
      var loadingMode: LoadingMode? = null

      logger.trace("Expected location of content directory {}", expectedContentDir)
      logger.trace("Expected location of theme directory {}", expectedThemeDir)

      Files.walkFileTree(absoluteHtDocs, object : SimpleFileVisitor<Path>() {
         override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
            logger.trace("Visit directory {}", dir)

            if (contentRootPathMatcher.matches(dir) && dir.parent == absoluteHtDocs) {
               logger.debug("Found content directory {}", dir)
               loadingMode = CONTENT
            } else if (themeRootPathMatcher.matches(dir) && dir.parent == absoluteHtDocs) {
               logger.debug("Found theme directory {}", dir)
               loadingMode = THEME
            }

            return CONTINUE
         }

         override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
            if (jenniePropertiesPathMatcher.doesNotMatch(file)) { // only load the root jennie.properties configuration file
               logger.trace("Preparing {}", file)

               when(loadingMode) {
                  CONTENT -> {
                     logger.trace("Capturing content file {}", file)
                     val relativePath = file.relativeToForBase(expectedContentDir)

                     contentListing[relativePath.toString()] = file
                  }
                  THEME -> {
                     logger.trace("Capturing theme file {}", file)
                     val relativePath = file.relativeToForBase(expectedThemeDir)

                     themeListing[relativePath.toString()] = file
                  }
               }
            }

            return CONTINUE
         }
      })

      return SiteMapping(
         theme = ThemeListing(themeListing.toImmutable()),
         content = ContentListing(contentListing.toImmutable()),
         configuration = configuration,
      )
   }

   private fun loadConfiguration(propertiesPath: Path): ImmutableMap<String, String> {

      return if (propertiesPath.isFileAndExists()) {
         val properties = Properties()

         properties.load(AutoCloseInputStream(Files.newInputStream(propertiesPath)))

         properties.stringPropertyNames().asSequence()
            .map { it to properties[it].toString() }
            .fold(Maps.mutable.empty()) { acc: MutableMap<String, String>, pair ->  acc[pair.first] = pair.second; acc }
            .toImmutable()
      } else {
         logger.debug("Using default configuration")

         Maps.immutable.empty()
      }
   }
}
