package tech.garymyers.web.site.mapping

import org.eclipse.collections.api.map.ImmutableMap
import tech.garymyers.web.site.mapping.content.ContentListing
import tech.garymyers.web.site.mapping.theme.ThemeListing

data class SiteMapping(
   val theme: ThemeListing,
   val content: ContentListing,
   val configuration: ImmutableMap<String, String>,
)
