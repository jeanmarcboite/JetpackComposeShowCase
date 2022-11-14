package box.example.showcase.applib.books.models

data class AuthorK(
    val author: AuthorKey
)

data class Book(
    val authors: List<AuthorK>,
    val covers: List<Int>,
    val created: Created,
    val description: Description,
    val excerpts: List<Excerpt>,
    val first_publish_date: String,
    val first_sentence: FirstSentence,
    val key: String,
    val last_modified: LastModified,
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

data class AuthorKey(
    val key: String
)

data class WBook(
    val authors: List<Author>,
    val classifications: Classifications,
    val contributions: List<String>,
    val covers: List<Int>,
    val created: Created,
    val first_sentence: FirstSentence,
    val identifiers: Identifiers,
    val isbn_10: List<String>,
    val isbn_13: List<String>,
    val key: String,
    val languages: List<Language>,
    val last_modified: LastModified,
    val latest_revision: Int,
    val local_id: List<String>,
    val number_of_pages: Int,
    val ocaid: String,
    val publish_date: String,
    val publishers: List<String>,
    val revision: Int,
    val source_records: List<String>,
    val title: String,
    val type: Type,
    val works: List<Work>
)

class Classifications


data class FirstSentence(
    val type: String,
    val value: String
)

data class Identifiers(
    val goodreads: List<String>,
    val librarything: List<String>
)

data class Language(
    val key: String
)

data class Work(
    val created: Created,
    val key: String,
    val last_modified: LastModified,
    val latest_revision: Int,
    val location: String,
    val revision: Int,
    val type: Type
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

