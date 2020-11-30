package tech.garymyers.web.site.mapping

import org.apache.commons.configuration2.Configuration
import org.apache.commons.configuration2.PropertiesConfiguration
import tech.garymyers.web.site.mapping.content.ContentListing
import tech.garymyers.web.site.mapping.theme.ThemeListing

data class SiteMapping(
   val theme: ThemeListing,
   val content: ContentListing,
   val configuration: Configuration,
)
