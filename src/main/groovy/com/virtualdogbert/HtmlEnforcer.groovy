package com.virtualdogbert

import groovy.transform.CompileStatic
import org.jsoup.Jsoup
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.util.regex.Pattern

@CompileStatic
class HtmlEnforcer {


    ConfigObject config

    HtmlEnforcer(String fileName = null) {
        File file
        ConfigSlurper configSlurper = new ConfigSlurper()

        if (fileName) {
            file = new File(fileName)
        } else {
            file = new File(getClass().getResource("/config.groovy").toURI())
        }

        config = configSlurper.parse(file.toURL())
    }

    boolean validateHtml(String html) {
        return (parseHtml(html, true, false).size() <= 0)
    }

    List<String> validateHtmlErrors(String html) {
        return parseHtml(html, false, false)
    }

    void enforceHtml(String html) {
        parseHtml(html)
    }

    List<String> parseHtml(String html, boolean returnFirstError = true, boolean throwException = true) {
        List<String> errors = []
        Document doc = Jsoup.parse(html)
        Elements elements = doc.body().select("*")

        for (Element element : elements) {
            if (!(element.tagName() in (Set<String>)config.allowedElements)) {

                if (throwException) {
                    throw new Exception("Element:${element.tagName()} is not allowed")
                } else if (returnFirstError) {
                    return ["Element:${element.tagName()} is not allowed".toString()]
                }

                errors << "Element:${element.tagName()} is not allowed".toString()
            }

            for (Attribute attribute : element.attributes()) {
                String value = attribute.value

                if (!(attribute.key in (Set<String>)config.allowedAttributes)) {

                    if (throwException) {
                        throw new Exception("Atribute: $attribute.key is not allowed for element: ${element.tagName()}")
                    } else if (returnFirstError) {
                        return ["Atribute: $attribute.key is not allowed for element: ${element.tagName()}".toString()]
                    }

                    errors << "Atribute: $attribute.key is not allowed for element: ${element.tagName()}".toString()
                }

                if (attribute.key == 'style') {

                    List<String> styles = value.split(';').toList()

                    for (String style : styles) {
                        String[] styleValues = style.split(':')

                        if (!(styleValues[0] in (Set<String>)config.allowedCssProperties)) {

                            if (throwException) {
                                throw new Exception("CSS Property: ${styleValues[0]} is not allowed.")
                            } else if (returnFirstError) {
                                return ["CSS Property: ${styleValues[0]} is not allowed.".toString()]
                            }

                            errors << "CSS Property: ${styleValues[0]} is not allowed.".toString()
                        }

                        Pattern check = (Pattern) config.cssPropertyPatterns[styleValues[0].trim()]

                        if (!(styleValues[1].trim() ==~ check)) {

                            if (throwException) {
                                throw new Exception("Value: ${styleValues[1]} is not allowed for CSS Property: ${styleValues[0]}")
                            } else if (returnFirstError) {
                                return ["Value: ${styleValues[1]} is not allowed for CSS Property: ${styleValues[0]}".toString()]
                            }

                            errors << "Value: ${styleValues[1]} is not allowed for CSS Property: ${styleValues[0]}".toString()
                        }
                    }
                } else {
                    Map check = (Map) config.attributePatterns[attribute.key]

                    if (!check.global && !((List<String>) check.elements).contains(element.tagName())) {

                        if (throwException) {
                            throw new Exception("Atribute: $attribute.key with value $attribute.value is not allowed for element: ${element.tagName()}")
                        } else if (returnFirstError) {
                            return ["Atribute: $attribute.key with value $attribute.value is not allowed for element: ${element.tagName()}".toString()]
                        }

                        errors << "Atribute: $attribute.key with value $attribute.value is not allowed for element: ${element.tagName()}".toString()
                    }

                    if (!(attribute.value ==~ (Pattern) check.pattern)) {

                        if (throwException) {
                            throw new Exception("Atribute: $attribute.key with value $attribute.value is not allowed for element: ${element.tagName()}")
                        } else if (returnFirstError) {
                            return ["Atribute: $attribute.key with value $attribute.value is not allowed for element: ${element.tagName()}".toString()]
                        }

                        errors << "Atribute: $attribute.key with value $attribute.value is not allowed for element: ${element.tagName()}".toString()
                    }
                }
            }
        }

        return errors
    }
}
