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


    HtmlConfig config

    HtmlEnforcer(HtmlConfig config) {
        this.config = config
    }

    boolean validateHtml(String html) {
        return (parseHtml(html, true, false).size() <= 0)
    }

    void enforceHtml(String html) {
        parseHtml(html)
    }

    List parseHtml(String html, boolean returnFirstError = true, boolean throwException = true) {
        List<String> errors = []
        Document doc = Jsoup.parse(html)
        Elements elements = doc.body().select("*")

        for (Element element : elements) {
            if (!(element.tagName() in config.allowedElements)) {
                //println(element.tagName())

                if (throwException) {
                    throw new Exception("Element:${element.tagName()} is not allowed")
                } else if (returnFirstError) {
                    return ["Element:${element.tagName()} is not allowed"]
                }

                errors << "Element:${element.tagName()} is not allowed".toString()
            }

            for (Attribute attribute : element.attributes()) {
                //println attribute.key
                //println attribute.value
                String value = attribute.value

                if (!(attribute.key in config.allowedAttributes)) {
                    //println attribute.key
                    //println attribute.value

                    if (throwException) {
                        throw new Exception("Atribute: $attribute.key is not allowed for element: ${element.tagName()}")
                    } else if (returnFirstError) {
                        return ["Atribute: $attribute.key is not allowed for element: ${element.tagName()}"]
                    }

                    errors << "Atribute: $attribute.key is not allowed for element: ${element.tagName()}".toString()
                }

                if (attribute.key == 'style') {

                    List<String> styles = value.split(';').toList()

                    for (String style : styles) {
                        String[] styleValues = style.split(':')

                        if (!(styleValues[0] in config.allowedCssProperties)) {

                            if (throwException) {
                                throw new Exception("CSS Property: ${styleValues[0]} is not allowed.")
                            } else if (returnFirstError) {
                                return ["CSS Property: ${styleValues[0]} is not allowed."]
                            }

                            errors << "CSS Property: ${styleValues[0]} is not allowed.".toString()
                        }

                        Pattern check = config.cssPropertyPatterns[styleValues[0].trim()]

                        if (!(styleValues[1].trim() ==~ check)) {

                            if (throwException) {
                                throw new Exception("Value: ${styleValues[1]} is not allowed for CSS Property: ${styleValues[0]}")
                            } else if (returnFirstError) {
                                return ["Value: ${styleValues[1]} is not allowed for CSS Property: ${styleValues[0]}"]
                            }

                            errors << "Value: ${styleValues[1]} is not allowed for CSS Property: ${styleValues[0]}".toString()
                        }
                    }
                } else {
                    Map check = config.attributePatterns[attribute.key]

                    if (!check.global && !((List<String>) check.elements).contains(element.tagName())) {

                        if (throwException) {
                            throw new Exception("Atribute: $attribute.key with value $attribute.value is not allowed for element: ${element.tagName()}")
                        } else if (returnFirstError) {
                            return ["Atribute: $attribute.key with value $attribute.value is not allowed for element: ${element.tagName()}"]
                        }

                        errors << "Atribute: $attribute.key with value $attribute.value is not allowed for element: ${element.tagName()}".toString()
                    }

                    if (!(attribute.value ==~ (Pattern) check.pattern)) {

                        if (throwException) {
                            throw new Exception("Atribute: $attribute.key with value $attribute.value is not allowed for element: ${element.tagName()}")
                        } else if (returnFirstError) {
                            return ["Atribute: $attribute.key with value $attribute.value is not allowed for element: ${element.tagName()}"]
                        }

                        errors << "Atribute: $attribute.key with value $attribute.value is not allowed for element: ${element.tagName()}".toString()
                    }
                    //println config.attributePatterns[attribute.key]
                }
            }
        }

        return errors

    }

    public static void main(String[] args) {
        HtmlEnforcer enforcer = new HtmlEnforcer(new HtmlConfig())
        Date d = new Date()
        //println enforcer.validateHtml("""<p id=5 style="color:black">test</p> <script>alert('powed');</script>""")
        //println enforcer.validateHtml("<a></a><p class='classy' style='color: blue;width:5%'>An <b>example</b> link.</p><img width=\"100\"></img>")
        //println enforcer.validateHtml("<p style='color: blue;width:5000em'></p>")
        //println enforcer.validateHtml("<p style='width:5000em'></p>")

        //        println enforcer.validateHtml("<p style='background:10px'></p>")   //TODO more work

        //println enforcer.validateHtml("<p style='background-attachment:fixed'></p>")
        //println enforcer.validateHtml("<p style='background-color:#FFFFFF'></p>")
        //println enforcer.validateHtml("""<p style='background-image:url("paper.gif")'></p>""")
        //println enforcer.validateHtml("""<p style="background-image:url('paper.gif')"></p>""")
        //println enforcer.validateHtml("""<p style="background-image:url('www.google.com/paper.gif')"></p>""")
        //println enforcer.validateHtml("<p style='background-position:cenTer ToP'></p>")
        //println enforcer.validateHtml("<p style='background-position:50px 50px'></p>")
        //println enforcer.validateHtml("<p style='background-position:10% 10%'></p>")
        //println enforcer.validateHtml("<p style='background-repeat:repeat-x'></p>")

        //        println enforcer.validateHtml("<p style='border:10px'></p>") //TODO more work
        //        println enforcer.validateHtml("<p style='border-bottom:10px'></p>")

        //println enforcer.validateHtml("<p style='border-bottom-color:red'></p>")
        // println enforcer.validateHtml("<p style='border-bottom-style:dotted'></p>")
        //println enforcer.validateHtml("<p style='border-bottom-width:medium'></p>")
        //println enforcer.validateHtml("<p style='border-bottom-width:50px'></p>")
        //println enforcer.validateHtml("<p style='border-color:#555'></p>")
        //        println enforcer.validateHtml("<p style='border-left:10px'></p>")
        //println enforcer.validateHtml("<p style='border-left-color:#213657'></p>")
        //println enforcer.validateHtml("<p style='border-left-style:dotted'></p>")
        //println enforcer.validateHtml("<p style='border-left-width:10px'></p>")
        //        println enforcer.validateHtml("<p style='border-right:10px'></p>")
        //println enforcer.validateHtml("<p style='border-right-color:GREEN'></p>")
        //println enforcer.validateHtml("<p style='border-right-style:dashed'></p>")
        //println enforcer.validateHtml("<p style='border-right-width:10px'></p>")
        //println enforcer.validateHtml("<p style='border-style:inset'></p>")
        //        println enforcer.validateHtml("<p style='border-top:10px'></p>")
        //println enforcer.validateHtml("<p style='border-top-color:#123'></p>")
        //println enforcer.validateHtml("<p style='border-top-style:groove'></p>")
        //println enforcer.validateHtml("<p style='border-top-width:10px'></p>")
        //println enforcer.validateHtml("<p style='border-width:10px'></p>")
        //        println enforcer.validateHtml("<p style='clear:10px'></p>")
        //        println enforcer.validateHtml("<p style='cursor:10px'></p>")
        //        println enforcer.validateHtml("<p style='display:10px'></p>")
        //        println enforcer.validateHtml("<p style='float:10px'></p>")
        //        println enforcer.validateHtml("<p style='position:10px'></p>")
        //        println enforcer.validateHtml("<p style='visibility:10px'></p>")
        //println enforcer.validateHtml("<p style='height:10px'></p>")
        //        println enforcer.validateHtml("<p style='line-height:10px'></p>")
        //        println enforcer.validateHtml("<p style='max-height:10px'></p>")
        //        println enforcer.validateHtml("<p style='max-width:10px'></p>")
        //        println enforcer.validateHtml("<p style='min-height:10px'></p>")
        //        println enforcer.validateHtml("<p style='min-width:10px'></p>")
        //        println enforcer.validateHtml("<p style='width:10px'></p>")
        //        println enforcer.validateHtml("<p style='font:10px'></p>")
        //        println enforcer.validateHtml("<p style='font-family:10px'></p>")
        //        println enforcer.validateHtml("<p style='font-size:10px'></p>")
        //        println enforcer.validateHtml("<p style='font-size-adjust:10px'></p>")
        //        println enforcer.validateHtml("<p style='font-stretch:10px'></p>")
        //        println enforcer.validateHtml("<p style='font-style:10px'></p>")
        //        println enforcer.validateHtml("<p style='font-variant:10px'></p>")
        //        println enforcer.validateHtml("<p style='font-weight:10px'></p>")
        //        println enforcer.validateHtml("<p style='content:10px'></p>")
        //        println enforcer.validateHtml("<p style='counter-increment:10px'></p>")
        //        println enforcer.validateHtml("<p style='counter-reset:10px'></p>")
        //        println enforcer.validateHtml("<p style='quotes:10px'></p>")
        //        println enforcer.validateHtml("<p style='list-style:10px'></p>")
        //        println enforcer.validateHtml("<p style='list-style-image:10px'></p>")
        //        println enforcer.validateHtml("<p style='list-style-position:10px'></p>")
        //        println enforcer.validateHtml("<p style='list-style-type:10px'></p>")
        //        println enforcer.validateHtml("<p style='marker-offset:10px'></p>")
        //        println enforcer.validateHtml("<p style='margin:10px'></p>")
        //        println enforcer.validateHtml("<p style='margin-bottom:10px'></p>")
        //        println enforcer.validateHtml("<p style='margin-left:10px'></p>")
        //        println enforcer.validateHtml("<p style='margin-right:10px'></p>")
        //        println enforcer.validateHtml("<p style='margin-top:10px'></p>")
        //        println enforcer.validateHtml("<p style='outline:10px'></p>")
        //println enforcer.validateHtml("<p style='outline-color:aqua'></p>")
        //        println enforcer.validateHtml("<p style='outline-style:10px'></p>")
        //        println enforcer.validateHtml("<p style='outline-width:10px'></p>")
        //        println enforcer.validateHtml("<p style='padding:10px'></p>")
        //        println enforcer.validateHtml("<p style='padding-bottom:10px'></p>")
        //        println enforcer.validateHtml("<p style='padding-left:10px'></p>")
        //        println enforcer.validateHtml("<p style='padding-right:10px'></p>")
        //        println enforcer.validateHtml("<p style='padding-top:10px'></p>")
        //        println enforcer.validateHtml("<p style='bottom:10px'></p>")
        //        println enforcer.validateHtml("<p style='clip:10px'></p>")
        //        println enforcer.validateHtml("<p style='left:10px'></p>")
        //        println enforcer.validateHtml("<p style='overflow:10px'></p>")
        //        println enforcer.validateHtml("<p style='position:10px'></p>")
        //        println enforcer.validateHtml("<p style='right:10px'></p>")
        //        println enforcer.validateHtml("<p style='top:10px'></p>")
        //        println enforcer.validateHtml("<p style='vertical-align:10px'></p>")
        //        println enforcer.validateHtml("<p style='z-index:10px'></p>")
        //        println enforcer.validateHtml("<p style='border-collapse:10px'></p>")
        //        println enforcer.validateHtml("<p style='border-spacing:10px'></p>")
        //        println enforcer.validateHtml("<p style='caption-side:10px'></p>")
        //        println enforcer.validateHtml("<p style='empty-cells:10px'></p>")
        //        println enforcer.validateHtml("<p style='table-layout:10px'></p>")
        //println enforcer.validateHtml("<p style='color:purple'></p>")
        //        println enforcer.validateHtml("<p style='direction:10px'></p>")
        //        println enforcer.validateHtml("<p style='line-height:10px'></p>")
        //        println enforcer.validateHtml("<p style='letter-spacing:10px'></p>")
        //        println enforcer.validateHtml("<p style='text-align:10px'></p>")
        //        println enforcer.validateHtml("<p style='text-decoration:10px'></p>")
        //        println enforcer.validateHtml("<p style='text-indent:10px'></p>")
        //        println enforcer.validateHtml("<p style='text-shadow:10px'></p>")
        //        println enforcer.validateHtml("<p style='text-transform:10px'></p>")
        //        println enforcer.validateHtml("<p style='unicode-bidi:10px'></p>")
        //        println enforcer.validateHtml("<p style='white-space:10px'></p>")
        //        println enforcer.validateHtml("<p style='word-spacing:10px'></p>")
        //        println enforcer.validateHtml("<p style='all:10px'></p>")
        //        println enforcer.validateHtml("<p style='aural:10px'></p>")
        //        println enforcer.validateHtml("<p style='braille:10px'></p>")
        //        println enforcer.validateHtml("<p style='embossed:10px'></p>")
        //        println enforcer.validateHtml("<p style='handheld:10px'></p>")
        //        println enforcer.validateHtml("<p style='print:10px'></p>")
        //        println enforcer.validateHtml("<p style='projection:10px'></p>")
        //        println enforcer.validateHtml("<p style='screen:10px'></p>")
        //        println enforcer.validateHtml("<p style='tty:10px'></p>")
        //        println enforcer.validateHtml("<p style='tv:10px'></p>")

        println "time ${new Date().time - d.time}"
    }
}
