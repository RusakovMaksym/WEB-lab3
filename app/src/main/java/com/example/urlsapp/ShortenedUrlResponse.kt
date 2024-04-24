data class ShortenedUrlResponse(
    val url: ShortenedUrl?
)

data class ShortenedUrl(
    val shortLink: String?,
    val fullLink: String?,
    val status: Int?
)