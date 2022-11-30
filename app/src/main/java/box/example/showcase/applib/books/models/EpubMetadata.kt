package box.example.showcase.applib.books.models

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class EpubContainer(val rootFile: String?) {
    companion object {
        fun parseXml(xml: String): EpubContainer {
            val TAG = "boxxxx [parseXml]"
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()

            xpp.setInput(StringReader(xml))
            var eventType = xpp.eventType
            var rootFile: String? = null
            while (eventType != XmlPullParser.END_DOCUMENT && rootFile == null) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.name == "rootfile") {
                        val rootfileI: Int? = (0..xpp.attributeCount - 1).find {
                            xpp.getAttributeName(it) == "full-path"
                        }
                        rootfileI?.apply {
                            rootFile = xpp.getAttributeValue(this)
                        }
                    }
                }
                eventType = xpp.next()
            }
            return EpubContainer(rootFile)
        }
    }

    override fun toString(): String {
        return "EpubContainer(rootFile='$rootFile')"
    }
}

class EpubMetadata(val title: String?) {
    init {
        Package.Metadata.title = title
    }

    var xmlVersion: String = "1.0"
    var encoding = "UTF-8"

    object Package {
        var xmlns: String? = null
        var uniqueIdentifier: String? = null
        var version: String? = null

        object Metadata {
            var xmlns_dc: String? = null
            var xmlns_dcterms: String? = null
            var xmlns_opf: String? = null
            var xmlns_xsi: String? = null

            object creator {
                var role: String? = null
                var fileAs: String? = null
                var value: String? = null
            }

            var title: String? = null

            object identifier {
                var id: String? = null
                var opfScheme: String? = null
                var value: String? = null
            }

            var publisher: String? = null

            object date {
                var opf_event: String? = null
                var value: String? = null
            }

            var dc_rights: String? = null
            var meta: List<Meta> = listOf()

            class Meta() {
                var name: String? = null
                var content: String? = null
            }

            var subject: List<String> = listOf()
            var language: List<String> = listOf()
        }


        var manifest: List<ManifestItem> = listOf()

        class ManifestItem {
            var href: String? = null
            var id: String? = null
            var mediaType: String? = null
        }

        object spine {
            var toc: String? = null
            var itemref: List<ItemRef> = listOf()

            class ItemRef {
                var idref: String? = null
                var linear: Boolean = false

            }
        }

        var guide: List<Reference> = listOf()

        class Reference {
            var href: String? = null
            var title: String? = null
            var type: String? = null
        }
    }

    companion object {
        fun parseXml(xml: String): EpubMetadata {
            class Tag(val attributes: Map<String, String>, val text: String)

            val TAG = "boxxxx [parseXml]"
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            val tags: MutableMap<String, Tag> = mutableMapOf()

            xpp.setInput(StringReader(xml))
            var eventType = xpp.eventType
            while (true) {
                eventType = xpp.next()
                when (eventType) {
                    XmlPullParser.END_DOCUMENT -> break
                    XmlPullParser.START_TAG -> {
                        val name = xpp.name
                        val attributes = mutableMapOf<String, String>()
                        (0..xpp.attributeCount - 1).forEach {
                            attributes[xpp.getAttributeName(it)] = xpp.getAttributeValue(it)
                        }
                        eventType = xpp.next()
                        if (eventType == XmlPullParser.TEXT) {
                            tags[name] = Tag(attributes, xpp.text)
                        }
                    }
                }
            }

            return EpubMetadata(tags["title"]?.text)
        }
    }

    override fun toString(): String {
        return "EpubMetadata(title='${Package.Metadata.title}')"
    }
}