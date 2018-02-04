/**
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package com.virtualdogbert

import groovy.transform.CompileStatic
import org.jsoup.Jsoup
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.util.regex.Pattern

/**
 * Just and idea I had bounding around my head, becasue I want to be able to
 * have white list detection for xss, but I don't want to sanitize the cogent,
 * I just want to check it and throw and error.  Current libraries I've found don't
 * cut it like Anti-Samy(no longer supported) and OWASP Java HTML Sanitizer which
 * leans toward sanitizing, changing the original html, making it hard to detect if
 * xxs was removed, which leads to false positives. So my idea it to parse through
 * all the elements and attributes seeing if they are allowed, also I would add checks
 * based on regexs for values. I'm thinking of adapting something like the
 * htmlPolicy.groovy(originally an example from Java HTML Sanitizer), but using
 * groovy maps for configuration:
 * https://github.com/OWASP/java-html-sanitizer/blob/master/src/main/java/org/owasp/html/examples/EbayPolicyExample.java
 *
 * I do intend of cleaning up the code and making this into an actual library. I'm now thinking that the config will move to just
 * be a Groovy config file that I slurp in, if that will work with the patterns.
 */
@CompileStatic
class HtmlEnforcer {


    ConfigObject config

    HtmlEnforcer(String fileName = null) {
        File file
        ConfigSlurper configSlurper = new ConfigSlurper()

        if (fileName) {
            file = new File(fileName)
        } else {
            file = new File(getClass().getResource("/DefaultConfig.groovy").toURI())
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
        String error = ''
        Document doc = Jsoup.parse(html)
        Elements elements = doc.body().select("*")

        for (Element element : elements) {
            error = handleElements(element, throwException)

            if (error) {
                if (returnFirstError) {
                    return [error]
                }

                errors << error
            }

            //TODO fix pattern for handling text
            //error = handleText(element.text(), throwException)

            if (error) {
                if (returnFirstError) {
                    return [error]
                }

                errors << error
            }

            for (Attribute attribute : element.attributes()) {
                String value = attribute.value
                error = handleAttributes(attribute, element, throwException)

                if (error) {
                    if (returnFirstError) {
                        return [error]
                    }

                    errors << error
                }

                if (attribute.key == 'style') {
                    List<String> styles = value.split(';').toList()

                    for (String style : styles) {
                        String[] styleValues = style.split(':')
                        error = handleStyle(styleValues[0], throwException)

                        if (error) {
                            if (returnFirstError) {
                                return [error]
                            }

                            errors << error
                        }

                        error = handleStyleValues(styleValues[0], styleValues[1], throwException)

                        if (error) {
                            if (returnFirstError) {
                                return [error]
                            }

                            errors << error
                        }
                    }
                } else {
                    Map check = (Map) config.attributePatterns[attribute.key]
                    error = "Atribute: $attribute.key with value $attribute.value is not allowed for element: ${element.tagName()}"
                    error = handleAttributeValuesGlobal(check, error, element, throwException)

                    if (error) {
                        if (returnFirstError) {
                            return [error]
                        }

                        errors << error
                    }

                    error = handleAttributeValues(attribute, check, error, throwException)

                    if (error) {
                        if (returnFirstError) {
                            return [error]
                        }

                        errors << error
                    }
                }
            }
        }

        return errors
    }

    String handleElements(Element element, boolean throwException = true) {

        if (!(element.tagName() in (Set<String>) config.allowedElements)) {
            String error = "Element:${element.tagName()} is not allowed".toString()

            if (throwException) {
                throw new Exception(error)
            }

            return error
        }

        return ''
    }

    String handleText(String text, boolean throwException = true) {
        if (!(text ==~ config.textPattern)) {
            String error = "Html text: $text is not allowed".toString()

            if (throwException) {
                throw new Exception(error)
            }

            return error
        }

        return ''
    }

    String handleAttributes(Attribute attribute, Element element, boolean throwException = true) {

        if (!(attribute.key in (Set<String>) config.allowedAttributes)) {
            String error = "Atribute: $attribute.key is not allowed for element: ${element.tagName()}"

            if (throwException) {
                throw new Exception(error)
            }
            return error
        }

        return ''
    }

    String handleAttributeValuesGlobal(Map check, String error, Element element, boolean throwException = true) {

        if (!check.global && !((List<String>) check.elements).contains(element.tagName())) {

            if (throwException) {
                throw new Exception(error)
            }

            return error
        }

        return ''
    }

    String handleAttributeValues(Attribute attribute, Map check, String error, boolean throwException = true) {
        if (!(attribute.value ==~ (Pattern) check.pattern)) {

            if (throwException) {
                throw new Exception(error)
            }
            return error
        }

        return ''
    }

    String handleStyle(String style, boolean throwException = true) {
        if (!(style in (Set<String>) config.allowedCssProperties)) {
            String error = "CSS Property: ${style} is not allowed."

            if (throwException) {
                throw new Exception(error)
            }

            return error
        }
        return ''
    }

    String handleStyleValues(String styleProperty, String styleValue, boolean throwException = true) {
        Pattern check = (Pattern) config.cssPropertyPatterns[styleProperty.trim()]

        if (!(styleValue.trim() ==~ check)) {
            String error = "Value: ${styleValue} is not allowed for CSS Property: ${styleProperty}"

            if (throwException) {
                throw new Exception(error)
            }

            return error
        }

        return ''
    }
}
