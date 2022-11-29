package box.example.showcase.applib.books.models.openlibrary


data class OpenLibraryBook(
    val authors: List<AuthorKey>?,
    val covers: List<Int>?,
    val created: Value?,
    val description: Value?,
    val excerpts: List<Excerpt>?,
    val first_publish_date: String?,
    val first_sentence: Value?,
    val key: String?,
    val last_modified: Value?,
    val latest_revision: Int?,
    val links: List<Link>?,
    val revision: Int?,
    val subject_people: List<String>?,
    val subject_places: List<String>?,
    val subjects: List<String>?,
    val title: String?,
    val type: Type?
)


data class Value(
    val type: String,
    val value: String
)

data class Excerpt(
    val author: AuthorKeyString,
    val comment: String,
    val excerpt: String
)

data class Link(
    val title: String,
    val type: Type,
    val url: String
)

data class AuthorKey(
    val author: AuthorKeyString
)

data class AuthorKeyString(
    val key: String
)

data class Type(
    val key: String
)

