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

import com.virtualdogbert.HtmlEnforcer
import spock.lang.Specification
import spock.lang.Unroll

class HtmlEnforcerSpec extends Specification {
    static HtmlEnforcer htmlEnforcer

    def setupSpec() {
        htmlEnforcer = new HtmlEnforcer()
        htmlEnforcer.validateHtml('')
    }

    @Unroll
    def "validateHtml passing #html with result #testResult"() {
        when:
            boolean result = htmlEnforcer.validateHtml(html)

        then:
            result == testResult

        where:
            html                                                                                                         || testResult
            ''                                                                                                           || true
            '<a></a>'                                                                                                    || true
            """<p id=5 style="color:black">test</p> <script>alert('powed');</script>"""                                  || false
            "<a></a><p class='classy' style='color: blue;width:5%'>An <b>example</b> link.</p><img width=\"100\"></img>" || true
            "<p style='color: blue;width:5000em'></p>"                                                                   || true
            "<p style='width:5000em'></p>"                                                                               || true
            "<p style='background-attachment:fixed'></p>"                                                                || true
            "<p style='background-color:#FFFFFF'></p>"                                                                   || true
            """<p style='background-image:url("paper.gif")'></p>"""                                                      || true
            """<p style="background-image:url('paper.gif')"></p>"""                                                      || true
            """<p style="background-image:url('www.google.com/paper.gif')"></p>"""                                       || true
            "<p style='background-position:cenTer ToP'></p>"                                                             || true
            "<p style='background-position:50px 50px'></p>"                                                              || true
            "<p style='background-position:10% 10%'></p>"                                                                || true
            "<p style='background-repeat:repeat-x'></p>"                                                                 || true
            "<p style='border-bottom-color:red'></p>"                                                                    || true
            "<p style='border-bottom-width:medium'></p>"                                                                 || true
            "<p style='border-bottom-width:50px'></p>"                                                                   || true
            "<p style='border-color:#555'></p>"                                                                          || true
            "<p style='border-left-color:#213657'></p>"                                                                  || true
            "<p style='border-left-style:dotted'></p>"                                                                   || true
            "<p style='border-left-width:10px'></p>"                                                                     || true
            "<p style='border-right-color:GREEN'></p>"                                                                   || true
            "<p style='border-right-style:dashed'></p>"                                                                  || true
            "<p style='border-right-width:10px'></p>"                                                                    || true
            "<p style='border-style:inset'></p>"                                                                         || true
            "<p style='border-top-color:#123'></p>"                                                                      || true
            "<p style='border-top-style:groove'></p>"                                                                    || true
            "<p style='border-top-width:10px'></p>"                                                                      || true
            "<p style='border-bottom-style:dotted'></p>"                                                                 || true
            "<p style='border-width:10px'></p>"                                                                          || true
            "<p style='height:10px'></p>"                                                                                || true
            "<p style='outline-color:aqua'></p>"                                                                         || true
            "<p style='color:purple'></p>"                                                                               || true

            //TODO replace 10px with a valid value and fix pattern in Default config
            "<p style='background:10px'></p>"                                                                            || true
            "<p style='border:10px'></p>"                                                                                || true
            "<p style='border-bottom:10px'></p>"                                                                         || true
            "<p style='border-left:10px'></p>"                                                                           || true
            "<p style='border-right:10px'></p>"                                                                          || true
            "<p style='border-top:10px'></p>"                                                                            || true
            "<p style='clear:10px'></p>"                                                                                 || true
            "<p style='cursor:10px'></p>"                                                                                || true
            "<p style='display:10px'></p>"                                                                               || true
            "<p style='float:10px'></p>"                                                                                 || true
            "<p style='position:10px'></p>"                                                                              || true
            "<p style='visibility:10px'></p>"                                                                            || true
            "<p style='line-height:10px'></p>"                                                                           || true
            "<p style='max-height:10px'></p>"                                                                            || true
            "<p style='max-width:10px'></p>"                                                                             || true
            "<p style='min-height:10px'></p>"                                                                            || true
            "<p style='min-width:10px'></p>"                                                                             || true
            "<p style='width:10px'></p>"                                                                                 || true
            "<p style='font:10px'></p>"                                                                                  || true
            "<p style='font-family:10px'></p>"                                                                           || true
            "<p style='font-size:10px'></p>"                                                                             || true
            "<p style='font-size-adjust:10px'></p>"                                                                      || true
            "<p style='font-stretch:10px'></p>"                                                                          || true
            "<p style='font-style:10px'></p>"                                                                            || true
            "<p style='font-variant:10px'></p>"                                                                          || true
            "<p style='font-weight:10px'></p>"                                                                           || true
            "<p style='content:10px'></p>"                                                                               || true
            "<p style='counter-increment:10px'></p>"                                                                     || true
            "<p style='counter-reset:10px'></p>"                                                                         || true
            "<p style='quotes:10px'></p>"                                                                                || true
            "<p style='list-style:10px'></p>"                                                                            || true
            "<p style='list-style-image:10px'></p>"                                                                      || true
            "<p style='list-style-position:10px'></p>"                                                                   || true
            "<p style='list-style-type:10px'></p>"                                                                       || true
            "<p style='marker-offset:10px'></p>"                                                                         || true
            "<p style='margin:10px'></p>"                                                                                || true
            "<p style='margin-bottom:10px'></p>"                                                                         || true
            "<p style='margin-left:10px'></p>"                                                                           || true
            "<p style='margin-right:10px'></p>"                                                                          || true
            "<p style='margin-top:10px'></p>"                                                                            || true
            "<p style='outline:10px'></p>"                                                                               || true
            "<p style='outline-style:10px'></p>"                                                                         || true
            "<p style='outline-width:10px'></p>"                                                                         || true
            "<p style='padding:10px'></p>"                                                                               || true
            "<p style='padding-bottom:10px'></p>"                                                                        || true
            "<p style='padding-left:10px'></p>"                                                                          || true
            "<p style='padding-right:10px'></p>"                                                                         || true
            "<p style='padding-top:10px'></p>"                                                                           || true
            "<p style='bottom:10px'></p>"                                                                                || true
            "<p style='clip:10px'></p>"                                                                                  || true
            "<p style='left:10px'></p>"                                                                                  || true
            "<p style='overflow:10px'></p>"                                                                              || true
            "<p style='position:10px'></p>"                                                                              || true
            "<p style='right:10px'></p>"                                                                                 || true
            "<p style='top:10px'></p>"                                                                                   || true
            "<p style='vertical-align:10px'></p>"                                                                        || true
            "<p style='z-index:10px'></p>"                                                                               || true
            "<p style='border-collapse:10px'></p>"                                                                       || true
            "<p style='border-spacing:10px'></p>"                                                                        || true
            "<p style='caption-side:10px'></p>"                                                                          || true
            "<p style='empty-cells:10px'></p>"                                                                           || true
            "<p style='table-layout:10px'></p>"                                                                          || true
            "<p style='direction:10px'></p>"                                                                             || true
            "<p style='line-height:10px'></p>"                                                                           || true
            "<p style='letter-spacing:10px'></p>"                                                                        || true
            "<p style='text-align:10px'></p>"                                                                            || true
            "<p style='text-decoration:10px'></p>"                                                                       || true
            "<p style='text-indent:10px'></p>"                                                                           || true
            "<p style='text-shadow:10px'></p>"                                                                           || true
            "<p style='text-transform:10px'></p>"                                                                        || true
            "<p style='unicode-bidi:10px'></p>"                                                                          || true
            "<p style='white-space:10px'></p>"                                                                           || true
            "<p style='word-spacing:10px'></p>"                                                                          || true
            "<p style='all:10px'></p>"                                                                                   || true
            "<p style='aural:10px'></p>"                                                                                 || true
            "<p style='braille:10px'></p>"                                                                               || true
            "<p style='embossed:10px'></p>"                                                                              || true
            "<p style='handheld:10px'></p>"                                                                              || true
            "<p style='print:10px'></p>"                                                                                 || true
            "<p style='projection:10px'></p>"                                                                            || true
            "<p style='screen:10px'></p>"                                                                                || true
            "<p style='tty:10px'></p>"                                                                                   || true
            "<p style='tv:10px'></p>"                                                                                    || true
    }

    @Unroll
    def "validateHtml xss #html with result #testResult"() {
        when:
            boolean result = htmlEnforcer.validateHtml(html)

        then:
            result == testResult

        where:
            html                                                                                                                                                                                                                                                          || testResult
            '>"><script>alert("XSS")</script>&'                                                                                                                                                                                                                           || false
            '"><STYLE>@import"javascript:alert(\'XSS\')";</STYLE>'                                                                                                                                                                                                        || false
            ">\"'><img%20src%3D%26%23x6a;%26%23x61;%26%23x76;%26%23x61;%26%23x73;%26%23x63;%26%23x72;%26%23x69;%26%23x70;%26%23x74;%26%23x3a;alert(%26quot;%26%23x20;XSS%26%23x20;Test%26%23x20;Successful%26quot;)>"                                                     || false
            ">%22%27><img%20src%3d%22javascript:alert(%27%20XSS%27)%22>"                                                                                                                                                                                                  || false
            "'%uff1cscript%uff1ealert('XSS')%uff1c/script%uff1e'"                                                                                                                                                                                                         || false
            """alert(String.fromCharCode(88,83,83))//";alert(String.fromCharCode(88,83,83))//--"""                                                                                                                                                                        || false
            "%3cscript src=http://www.example.com/malicious-code.js%3e%3c/script%3e"                                                                                                                                                                                      || false
            "'';!--\"<XSS>=&{()}"                                                                                                                                                                                                                                         || false
            "<IMG SRC=\"javascript:alert('XSS');\">"                                                                                                                                                                                                                      || false
            "<IMG SRC=javascript:alert('XSS')>"                                                                                                                                                                                                                           || false
            "<IMG SRC=JaVaScRiPt:alert('XSS')>"                                                                                                                                                                                                                           || false
            "<IMG SRC=JaVaScRiPt:alert(&quot;XSS<WBR>&quot;)>"                                                                                                                                                                                                            || false
            "<IMGSRC=&#106;&#97;&#118;&#97;&<WBR>#115;&#99;&#114;&#105;&#112;&<WBR>#116;&#58;&#97;&#108;&#101;&<WBR>#114;&#116;&#40;&#39;&#88;&#83<WBR>;&#83;&#39;&#41>"                                                                                                  || false
            "<IMGSRC=&#0000106&#0000097&<WBR>#0000118&#0000097&#0000115&<WBR>#0000099&#0000114&#0000105&<WBR>#0000112&#0000116&#0000058&<WBR>#0000097&#0000108&#0000101&<WBR>#0000114&#0000116&#0000040&<WBR>#0000039&#0000088&#0000083&<WBR>#0000083&#0000039&#0000041>" || false
            "<IMGSRC=&#x6A&#x61&#x76&#x61&#x73&<WBR>#x63&#x72&#x69&#x70&#x74&#x3A&<WBR>#x61&#x6C&#x65&#x72&#x74&#x28&<WBR>#x27&#x58&#x53&#x53&#x27&#x29>"                                                                                                                 || false
            "<IMG SRC=\"jav&#x09;ascript:alert(<WBR>'XSS');\">"                                                                                                                                                                                                           || false
            "<IMG SRC=\"jav&#x0A;ascript:alert(<WBR>'XSS');\">"                                                                                                                                                                                                           || false
            "<IMG SRC=\"jav&#x0D;ascript:alert(<WBR>'XSS');\">"                                                                                                                                                                                                           || false
    }
}
