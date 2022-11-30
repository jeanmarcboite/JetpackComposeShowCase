package box.tools.xml

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class XmlTag(
    val name: String,
    val attributes: Map<String, String>,
    val text: String,
    val children: List<XmlTag>
)

class XmlParser {
    companion object {
        fun parse(xpp: XmlPullParser): XmlTag? {
            if (xpp.eventType == XmlPullParser.START_TAG) {
                var text = ""
                var children: MutableList<XmlTag> = mutableListOf()
                val name = xpp.name  // name of START_TAG
                val attributes = mutableMapOf<String, String>()
                (0..xpp.attributeCount - 1).forEach {
                    attributes[xpp.getAttributeName(it)] = xpp.getAttributeValue(it)
                }

                xpp.next()
                while (xpp.eventType != XmlPullParser.END_TAG) {
                    if (xpp.eventType == XmlPullParser.TEXT)
                        text = xpp.text
                    if (xpp.eventType == XmlPullParser.START_TAG) {
                        var child: XmlTag? = null
                        do {
                            child = parse(xpp)
                            child?.apply { children.add(this) }
                        } while (child != null)
                    }
                    xpp.next()
                }
                return XmlTag(name, attributes, text, children)
            }

            return null
        }

        fun parse(xmlString: String): XmlTag? {
            @Suppress("UNUSED_VARIABLE") val TAG = "boxxxx [parseXml]"
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            val tags: MutableMap<String, XmlTag> = mutableMapOf()

            xpp.setInput(StringReader(xmlString))
            Log.v("boxxx", "start with event type ${xpp.eventType}")
            xpp.next()
            if (xpp.eventType == XmlPullParser.START_DOCUMENT) {
                xpp.next()
                return parse(xpp)
            }

            return null
        }
    }
}