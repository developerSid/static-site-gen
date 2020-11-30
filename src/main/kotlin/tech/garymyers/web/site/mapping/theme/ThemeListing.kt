package tech.garymyers.web.site.mapping.theme

import org.eclipse.collections.api.factory.Maps
import java.nio.file.Path

data class ThemeListing(
   val mapping: MutableMap<String, Path> = Maps.mutable.empty(),
) {
}