package tech.garymyers.web.site.mapping

import tech.garymyers.web.site.content.ContentListing
import tech.garymyers.web.site.theme.ThemeListing

data class SiteMapping(
   val theme: ThemeListing,
   val content: ContentListing,
)
