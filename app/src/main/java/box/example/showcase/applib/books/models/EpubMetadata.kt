package box.example.showcase.applib.books.models

import box.tools.xml.XmlTag
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.lang.ref.Reference

class EpubContainer(val rootFile: String?) {
    companion object {
        fun parseXml(xml: String): EpubContainer {
            @Suppress("UNUSED_VARIABLE") val TAG = "boxxxx [parseXml]"
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

class EpubMetadata(tags: Map<String, XmlTag>) {
    var xmlVersion: String = "1.0"
    var encoding = "UTF-8"

    class _package {
        var xmlns: String? = null
        var uniqueIdentifier: String? = null
        var version: String? = null

        object metadata {
            var xmlns_dc: String? = null
            var xmlns_dcterms: String? = null
            var xmlns_opf: String? = null
            var xmlns_xsi: String? = null

            val creator = object {
                var role: String? = null
                var fileAs: String? = null
                var value: String? = null
            }

            val title: String? = tags["title"]?.text

            val identifier = object {
                var id: String? = null
                var opfScheme: String? = null
                var value: String? = null
            }

            var publisher: String? = null

            val date = object {
                var opf_event: String? = null
                var value: String? = null
            }

            var dc_rights: String? = null
            var meta: List<Meta> = listOf()

            inner class Meta {
                var name: String? = null
                var content: String? = null
            }

            var subject: List<String> = listOf()
            var language: List<String> = listOf()
        }


        var manifest: List<ManifestItem> = listOf()

        inner class ManifestItem {
            var href: String? = null
            var id: String? = null
            var mediaType: String? = null
        }

        val spine = object {
            var toc: String? = null
            var itemref: List<ItemRef> = listOf()

            inner class ItemRef {
                var idref: String? = null
                var linear: Boolean = false

            }
        }

        var guide: List<Reference> = listOf()

        inner class Reference {
            var href: String? = null
            var title: String? = null
            var type: String? = null
        }
    }

    companion object {
        fun parseXml(xml: String): EpubMetadata {


            @Suppress("UNUSED_VARIABLE") val TAG = "boxxxx [parseXml]"
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            val tags: MutableMap<String, XmlTag> = mutableMapOf()

            xpp.setInput(StringReader(xml))
            xpp.eventType
            while (true) {
                when (xpp.next()) {
                    XmlPullParser.END_DOCUMENT -> break
                    XmlPullParser.START_TAG -> {
                        val name = xpp.name
                        val attributes = mutableMapOf<String, String>()
                        (0..xpp.attributeCount - 1).forEach {
                            attributes[xpp.getAttributeName(it)] = xpp.getAttributeValue(it)
                        }
                        if (xpp.next() == XmlPullParser.TEXT) {
                            tags[name] = XmlTag(attributes, xpp.text)
                        }
                    }
                }
            }

            return EpubMetadata()
        }
    }

    override fun toString(): String {
        return "EpubMetadata(title='${_package().metadata}')"
    }
}