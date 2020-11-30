package tech.garymyers.web.site.content

import java.nio.file.Path

data class ContentListing(
   val mapping: MutableMap<String, Path>
)
