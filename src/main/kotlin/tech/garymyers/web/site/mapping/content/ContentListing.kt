package tech.garymyers.web.site.mapping.content

import java.nio.file.Path

data class ContentListing(
   val mapping: MutableMap<String, Path> = mutableMapOf()
)
