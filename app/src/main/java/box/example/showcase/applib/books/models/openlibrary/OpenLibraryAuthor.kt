package box.example.showcase.applib.books.models.openlibrary

data class OpenLibraryAuthor(
    val alternate_names: List<String>?,
    val bio: String?,
    val birth_date: String?,
    val created: Value?,
    val death_date: String?,
    val fuller_name: String?,
    val key: String?,
    val last_modified: Value?,
    val latest_revision: Int?,
    val links: List<Link>?,
    val name: String?,
    val personal_name: String?,
    val photos: List<Int>?,
    val remote_ids: RemoteIds?,
    val revision: Int?,
    val source_records: List<String>?,
    val type: Type?,
    val wikipedia: String?
)

data class RemoteIds(
    val isni: String?,
    val viaf: String?,
    val wikidata: String
)
