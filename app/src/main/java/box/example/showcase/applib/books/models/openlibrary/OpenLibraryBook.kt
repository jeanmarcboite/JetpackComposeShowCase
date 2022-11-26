package box.example.showcase.applib.books.models.openlibrary


data class OpenLibraryBook(
    val authors: List<AuthorK>,
    val covers: List<Int>?,
    val created: Created?,
    val description: Description?,
    val excerpts: List<Excerpt>?,
    val first_publish_date: String,
    val first_sentence: FirstSentence?,
    val key: String,
    val last_modified: LastModified?,
    val latest_revision: Int,
    val links: List<Link>,
    val revision: Int,
    val subject_people: List<String>,
    val subject_places: List<String>,
    val subjects: List<String>,
    val title: String,
    val type: Type
)


data class Description(
    val type: String,
    val value: String
)

data class Excerpt(
    val author: AuthorKey,
    val comment: String,
    val excerpt: String
)

data class Link(
    val title: String,
    val type: Type,
    val url: String
)

data class AuthorK(
    val author: AuthorKey
)

data class AuthorKey(
    val key: String
)


data class FirstSentence(
    val type: String,
    val value: String
)

data class Created(
    val type: String,
    val value: String
)

data class LastModified(
    val type: String,
    val value: String
)

data class Type(
    val key: String
)
