package tech.garymyers.web.site.mapping.content

import org.eclipse.collections.api.factory.Maps
import org.eclipse.collections.api.map.ImmutableMap
import java.nio.file.Path

data class ContentListing(
   val mapping: ImmutableMap<String, Path> = Maps.immutable.empty()
)
