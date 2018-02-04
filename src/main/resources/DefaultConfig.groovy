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
import java.util.regex.Pattern

//TODO figure out what I need to do validate text. This might be big enough to require its own groovy class.
//TEXT = Pattern.compile()

// The 16 colors defined by the HTML Spec (also used by the CSS Spec)
COLOR_NAME = Pattern.compile(/(?i:aqua|black|blue|fuchsia|gray|grey|green|lime|maroon|navy|olive|purple|red|silver|teal|white|yellow)/)
// HTML/CSS Spec allows 3 or 6 digit hex to specify color
COLOR_CODE = Pattern.compile('(?:#(?:[0-9a-fA-F]{3}(?:[0-9a-fA-F]{3})?))');
NUMBER_OR_PERCENT = Pattern.compile('[0-9]+%?')
PARAGRAPH = Pattern.compile('(?:[\\p{L}\\p{N},\'\\.\\s\\-_\\(\\)]|&[0-9]{2};)*')
HTML_ID = Pattern.compile('[a-zA-Z0-9\\:\\-_\\.]+')
// force non-empty with a '+' at the end instead of '*'
HTML_TITLE = Pattern.compile('[\\p{L}\\p{N}\\s\\-_\',:\\[\\]!\\./\\\\\\(\\)&]*')
HTML_CLASS = Pattern.compile('[a-zA-Z0-9\\s,\\-_]+')

ONSITE_URL = Pattern.compile('(?:[\\p{L}\\p{N}\\\\\\.\\#@\\$%\\+&;\\-_~,\\?=/!]+|\\#(\\w)+)')
OFFSITE_URL = Pattern.compile(
        '\\s*(?:(?:ht|f)tps?://|mailto:)[\\p{L}\\p{N}][\\p{L}\\p{N}\\p{Zs}\\.\\#@\\$%\\+&;:\\-_~,\\?=/!\\(\\)]*+\\s*')

NUMBER = Pattern.compile('[+-]?(?:(?:[0-9]+(?:\\.[0-9]*)?)|\\.[0-9]+)')
NAME = Pattern.compile('[a-zA-Z0-9\\-_\\$]+')
ALIGN = Pattern.compile('(?i)center|left|right|justify|char')
VALIGN = Pattern.compile('(?i)baseline|bottom|middle|top')
COLOR_NAME_OR_COLOR_CODE = Pattern.compile("${COLOR_NAME.pattern()}|${COLOR_CODE.pattern()}")
ONSITE_OR_OFFSITE_URL = Pattern.compile("${ONSITE_URL.pattern()}|${OFFSITE_URL.pattern()}")
BLANK = Pattern.compile("")
NO_RESIZE = Pattern.compile('(?i)noresize')
SCOPE = Pattern.compile('(?i)(?:row|col)(?:group)?')
FACE = Pattern.compile('[\\w;, \\-]+')
LANG = Pattern.compile('[a-zA-Z]{2,20}')
AUTO_INITIAL = Pattern.compile(/(?i:auto|initial)/)
INITIAL_INHERIRIT_TRANSPARENT = Pattern.compile(/(?i:initial|inherit|transparent)/)
LENGTH = Pattern.compile('[+-]?(?:(?:[0-9]+(?:\\.[0-9]*)?)|\\.[0-9]+)(em|ex|%|px|cm|mm|in|pt|pc)$')
CORD = Pattern.compile('[+-]?(?:(?:[0-9]+(?:\\.[0-9]*)?)|\\.[0-9]+)(em|ex|%|px|cm|mm|in|pt|pc)')
AUTO_INITIAL_OR_LENGTH = Pattern.compile("${AUTO_INITIAL.pattern()}|${LENGTH.pattern()}")
FIXED_OR_SCROLL = Pattern.compile(/(?i:fixed|scroll)/)
BACKGROUND_URL = Pattern.compile("url\\((\"|\')${ONSITE_URL.pattern()}(\"|\')\\)|url\\((\"|\')${OFFSITE_URL.pattern()}(\"|\')\\)")
ONE_CHAR = Pattern.compile('.?', Pattern.DOTALL)
POSITION = Pattern.compile("${AUTO_INITIAL.pattern()}|${CORD.pattern()} ${CORD.pattern()}\$")
POSITIONS = Pattern.compile(/(?i:center|top|bottom|top left|top center|top right|center left|center center|center right|center top|center bottom|bottom left|bottom center|bottom right|left top|left center|left bottom|right top|right center|right bottom)/)
REPEAT = Pattern.compile(/(?i:repeat|repeat-x|repeat-y|no-repeat|space|round|initial|inherit)/)
BORDER_STYLE = Pattern.compile(/(?i:inone|hidden|dotted|dashed|solid|double|groove|ridge|inset|outset|initial|inherit)/)
BORDER_WIDTH = Pattern.compile(/(?i:medium|thin|thick|length|initial|inherit )/)


allowedElements = [
        'a', 'label', 'noscript', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6',
        'p', 'i', 'b', 'u', 'strong', 'em', 'small', 'big', 'pre', 'code',
        'cite', 'samp', 'sub', 'sup', 'strike', 'center', 'blockquote',
        'hr', 'br', 'col', 'font', 'map', 'span', 'div', 'img',
        'ul', 'ol', 'li', 'dd', 'dt', 'dl', 'tbody', 'thead', 'tfoot',
        'table', 'td', 'th', 'tr', 'colgroup', 'fieldset', 'legend', 'body'
]

attributePatterns = [
        style      : [pattern: null, global: true, elements: []],
        id         : [pattern: HTML_ID, global: true, elements: []],
        'class'    : [pattern: HTML_CLASS, global: true, elements: []],
        lang       : [pattern: LANG, global: true, elements: []],
        title      : [pattern: HTML_TITLE, global: true, elements: []],
        src        : [pattern: ONSITE_OR_OFFSITE_URL, global: false, elements: ['img']],
        axis       : [pattern: NAME, global: false, elements: ['td', 'th']],
        headers    : [pattern: NAME, global: false, elements: ['td', 'th']],
        face       : [pattern: FACE, global: false, elements: ['font']],
        nohref     : [pattern: BLANK, global: false, elements: ['a']],
        name       : [pattern: NAME, global: false, elements: ['a', 'img', 'table']],
        border     : [pattern: NUMBER, global: false, elements: ['img']],
        hspace     : [pattern: NUMBER, global: false, elements: ['img']],
        vspace     : [pattern: NUMBER, global: false, elements: ['img']],
        cellpadding: [pattern: NUMBER, global: false, elements: ['table']],
        cellspacing: [pattern: NUMBER, global: false, elements: ['table']],
        noresize   : [pattern: NO_RESIZE, global: false, elements: ['table']],
        scope      : [pattern: SCOPE, global: false, elements: ['td', 'th']],
        nowrap     : [pattern: BLANK, global: false, elements: ['td', 'th']],
        height     : [pattern: NUMBER_OR_PERCENT, global: false, elements: ['table', 'td', 'th', 'tr', 'img']],
        width      : [pattern: NUMBER_OR_PERCENT, global: false, elements: ['table', 'td', 'th', 'tr', 'img', 'colgroup', 'col']],
        colspan    : [pattern: NUMBER, global: false, elements: ['td', 'th']],
        rowspan    : [pattern: NUMBER, global: false, elements: ['td', 'th']],
        align      : [patern: ALIGN, global: false, elements: ['p', 'table', 'thead', 'tbody', 'tfoot', 'img', 'td', 'th', 'tr', 'colgroup', 'col']],
        'for'      : [patern: HTML_ID, global: false, elements: ['label']],
        color      : [patern: COLOR_NAME_OR_COLOR_CODE, global: false, elements: ['font']],
        size       : [patern: NUMBER, global: false, elements: ['font']],
        href       : [patern: ONSITE_OR_OFFSITE_URL, global: false, elements: ['a']],
        alt        : [patern: PARAGRAPH, global: false, elements: ['img']],
        bgcolor    : [patern: COLOR_NAME_OR_COLOR_CODE, global: false, elements: ['table', 'td', 'th']],
        background : [patern: ONSITE_URL, global: false, elements: ['table', 'td', 'th', 'tr']],
        abbr       : [patern: PARAGRAPH, global: false, elements: ['td', 'th']],
        valign     : [patern: VALIGN, global: false, elements: ['thead', 'tbody', 'tfoot', 'td', 'th', 'tr', 'colgroup', 'col']],
        charoff    : [patern: NUMBER_OR_PERCENT, global: false, elements: ['td', 'th', 'tr', 'colgroup', 'col', 'thead', 'tbody', 'tfoot']],
        'char'     : [patern: ONE_CHAR, global: false, elements: ['td', 'th', 'tr', 'colgroup', 'col', 'thead', 'tbody', 'tfoot']]
]

allowedAttributes = attributePatterns.keySet()

PATTERN = Pattern.compile('') //TODO just a stand in so the code compiles till I replace all the css patterns

cssPropertyPatterns = [
        'background'           : PATTERN,
        'background-attachment': FIXED_OR_SCROLL,
        'background-color'     : Pattern.compile("${INITIAL_INHERIRIT_TRANSPARENT.pattern()}|${COLOR_NAME_OR_COLOR_CODE.pattern()}"),
        'background-image'     : BACKGROUND_URL,
        'background-position'  : Pattern.compile("${POSITION.pattern()}|${POSITIONS.pattern()}"),
        'background-repeat'    : REPEAT,
        'border'               : PATTERN,
        'border-bottom'        : PATTERN,
        'border-bottom-color'  : COLOR_NAME_OR_COLOR_CODE,
        'border-bottom-style'  : BORDER_STYLE,
        'border-bottom-width'  : Pattern.compile("${BORDER_WIDTH.pattern()}|${LENGTH.pattern()}"),
        'border-color'         : COLOR_NAME_OR_COLOR_CODE,
        'border-left'          : PATTERN,
        'border-left-color'    : COLOR_NAME_OR_COLOR_CODE,
        'border-left-style'    : BORDER_STYLE,
        'border-left-width'    : Pattern.compile("${BORDER_WIDTH.pattern()}|${LENGTH.pattern()}"),
        'border-right'         : PATTERN,
        'border-right-color'   : COLOR_NAME_OR_COLOR_CODE,
        'border-right-style'   : BORDER_STYLE,
        'border-right-width'   : Pattern.compile("${BORDER_WIDTH.pattern()}|${LENGTH.pattern()}"),
        'border-style'         : BORDER_STYLE,
        'border-top'           : PATTERN,
        'border-top-color'     : COLOR_NAME_OR_COLOR_CODE,
        'border-top-style'     : BORDER_STYLE,
        'border-top-width'     : Pattern.compile("${BORDER_WIDTH.pattern()}|${LENGTH.pattern()}"),
        'border-width'         : Pattern.compile("${BORDER_WIDTH.pattern()}|${LENGTH.pattern()}"),
        'clear'                : PATTERN,
        'cursor'               : PATTERN,
        'display'              : PATTERN,
        'float'                : PATTERN,
        'position'             : PATTERN,
        'visibility'           : PATTERN,
        'height'               : AUTO_INITIAL_OR_LENGTH,
        'line-height'          : PATTERN,
        'max-height'           : PATTERN,
        'max-width'            : PATTERN,
        'min-height'           : PATTERN,
        'min-width'            : PATTERN,
        'width'                : AUTO_INITIAL_OR_LENGTH,
        'font'                 : PATTERN,
        'font-family'          : PATTERN,
        'font-size'            : PATTERN,
        'font-size-adjust'     : PATTERN,
        'font-stretch'         : PATTERN,
        'font-style'           : PATTERN,
        'font-variant'         : PATTERN,
        'font-weight'          : PATTERN,
        'content'              : PATTERN,
        'counter-increment'    : PATTERN,
        'counter-reset'        : PATTERN,
        'quotes'               : PATTERN,
        'list-style'           : PATTERN,
        'list-style-image'     : PATTERN,
        'list-style-position'  : PATTERN,
        'list-style-type'      : PATTERN,
        'marker-offset'        : PATTERN,
        'margin'               : PATTERN,
        'margin-bottom'        : PATTERN,
        'margin-left'          : PATTERN,
        'margin-right'         : PATTERN,
        'margin-top'           : PATTERN,
        'outline'              : PATTERN,
        'outline-color'        : COLOR_NAME_OR_COLOR_CODE,
        'outline-style'        : PATTERN,
        'outline-width'        : PATTERN,
        'padding'              : PATTERN,
        'padding-bottom'       : PATTERN,
        'padding-left'         : PATTERN,
        'padding-right'        : PATTERN,
        'padding-top'          : PATTERN,
        'bottom'               : PATTERN,
        'clip'                 : PATTERN,
        'left'                 : PATTERN,
        'overflow'             : PATTERN,
        'right'                : PATTERN,
        'top'                  : PATTERN,
        'vertical-align'       : PATTERN,
        'z-index'              : PATTERN,
        'border-collapse'      : PATTERN,
        'border-spacing'       : PATTERN,
        'caption-side'         : PATTERN,
        'empty-cells'          : PATTERN,
        'table-layout'         : PATTERN,
        'color'                : COLOR_NAME_OR_COLOR_CODE,
        'direction'            : PATTERN,
        'letter-spacing'       : PATTERN,
        'text-align'           : PATTERN,
        'text-decoration'      : PATTERN,
        'text-indent'          : PATTERN,
        'text-shadow'          : PATTERN,
        'text-transform'       : PATTERN,
        'unicode-bidi'         : PATTERN,
        'white-space'          : PATTERN,
        'word-spacing'         : PATTERN,
        'all'                  : PATTERN,
        'aural'                : PATTERN,
        'braille'              : PATTERN,
        'embossed'             : PATTERN,
        'handheld'             : PATTERN,
        'print'                : PATTERN,
        'projection'           : PATTERN,
        'screen'               : PATTERN,
        'tty'                  : PATTERN,
        'tv'                   : PATTERN
]

allowedCssProperties = cssPropertyPatterns.keySet()

