package com.virtualdogbert

import groovy.transform.CompileStatic

import java.util.regex.Pattern

@CompileStatic
class HtmlConfig {

  // The 16 colors defined by the HTML Spec (also used by the CSS Spec)
  static final Pattern COLOR_NAME = Pattern.compile(/(?i:aqua|black|blue|fuchsia|gray|grey|green|lime|maroon|navy|olive|purple|red|silver|teal|white|yellow)/)
  // HTML/CSS Spec allows 3 or 6 digit hex to specify color
  static final Pattern COLOR_CODE = Pattern.compile('(?:#(?:[0-9a-fA-F]{3}(?:[0-9a-fA-F]{3})?))');
  static final Pattern NUMBER_OR_PERCENT = Pattern.compile('[0-9]+%?')
  static final Pattern PARAGRAPH = Pattern.compile('(?:[\\p{L}\\p{N},\'\\.\\s\\-_\\(\\)]|&[0-9]{2};)*')
  static final Pattern HTML_ID = Pattern.compile('[a-zA-Z0-9\\:\\-_\\.]+')
  // force non-empty with a '+' at the end instead of '*'
  static final Pattern HTML_TITLE = Pattern.compile('[\\p{L}\\p{N}\\s\\-_\',:\\[\\]!\\./\\\\\\(\\)&]*')

  static final Pattern ALLOWED_CLASSES = Pattern.compile(/(?i:auto|initial)/)
  static final Pattern HTML_CLASS = Pattern.compile('[a-zA-Z0-9\\s,\\-_]+')

  static final Pattern ONSITE_URL = Pattern.compile('(?:[\\p{L}\\p{N}\\\\\\.\\#@\\$%\\+&;\\-_~,\\?=/!]+|\\#(\\w)+)')
  static final Pattern OFFSITE_URL = Pattern.compile(
      '\\s*(?:(?:ht|f)tps?://|mailto:)[\\p{L}\\p{N}][\\p{L}\\p{N}\\p{Zs}\\.\\#@\\$%\\+&;:\\-_~,\\?=/!\\(\\)]*+\\s*')

  static final Pattern NUMBER = Pattern.compile('[+-]?(?:(?:[0-9]+(?:\\.[0-9]*)?)|\\.[0-9]+)')
  static final Pattern NAME = Pattern.compile('[a-zA-Z0-9\\-_\\$]+')
  static final Pattern ALIGN = Pattern.compile('(?i)center|left|right|justify|char')
  static final Pattern VALIGN = Pattern.compile('(?i)baseline|bottom|middle|top')
  static final Pattern COLOR_NAME_OR_COLOR_CODE = Pattern.compile("${COLOR_NAME.pattern()}|${COLOR_CODE.pattern()}")
  static final Pattern ONSITE_OR_OFFSITE_URL = Pattern.compile("${ONSITE_URL.pattern()}|${OFFSITE_URL.pattern()}")
  static final Pattern BLANK = Pattern.compile("")
  static final Pattern NO_RESIZE = Pattern.compile('(?i)noresize')
  static final Pattern SCOPE = Pattern.compile('(?i)(?:row|col)(?:group)?')
  static final Pattern FACE =Pattern.compile('[\\w;, \\-]+')
  static final Pattern LANG = Pattern.compile('[a-zA-Z]{2,20}')
  static final Pattern AUTO_INITIAL = Pattern.compile(/(?i:auto|initial)/)
  static final Pattern INITIAL_INHERIRIT_TRANSPARENT = Pattern.compile(/(?i:initial|inherit|transparent)/)
  static final Pattern LENGTH = Pattern.compile('[+-]?(?:(?:[0-9]+(?:\\.[0-9]*)?)|\\.[0-9]+)(em|ex|%|px|cm|mm|in|pt|pc)$')
  static final Pattern CORD = Pattern.compile('[+-]?(?:(?:[0-9]+(?:\\.[0-9]*)?)|\\.[0-9]+)(em|ex|%|px|cm|mm|in|pt|pc)')
  static final Pattern AUTO_INITIAL_OR_LENGTH = Pattern.compile("${AUTO_INITIAL.pattern()}|${LENGTH.pattern()}")
  static final Pattern FIXED_OR_SCROLL = Pattern.compile(/(?i:fixed|scroll)/)
  static final Pattern CSS_URL = Pattern.compile("url\\((\"|\')${ONSITE_URL.pattern()}(\"|\')\\)|url\\((\"|\')${OFFSITE_URL.pattern()}(\"|\')\\)")
  static final Pattern ONE_CHAR = Pattern.compile('.?', Pattern.DOTALL)
  static final Pattern POSITION = Pattern.compile("${AUTO_INITIAL.pattern()}|${CORD.pattern()} ${CORD.pattern()}\$")
  static final Pattern POSITIONS = Pattern.compile(/(?i:center|top|bottom|top left|top center|top right|center left|center center|center right|center top|center bottom|bottom left|bottom center|bottom right|left top|left center|left bottom|right top|right center|right bottom)/)
  static final Pattern REPEAT = Pattern.compile(/(?i:repeat|repeat-x|repeat-y|no-repeat|space|round|initial|inherit)/)
  static final Pattern BORDER_STYLE = Pattern.compile(/(?i:inone|hidden|dotted|dashed|solid|double|groove|ridge|inset|outset|initial|inherit)/)
  static final Pattern BORDER_WIDTH = Pattern.compile(/(?i:medium|thin|thick|length|initial|inherit )/)


   static final List<String> allowedElements = ['a', 'label', 'noscript', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6',
              'p', 'i', 'b', 'u', 'strong', 'em', 'small', 'big', 'pre', 'code',
              'cite', 'samp', 'sub', 'sup', 'strike', 'center', 'blockquote',
              'hr', 'br', 'col', 'font', 'map', 'span', 'div', 'img',
              'ul', 'ol', 'li', 'dd', 'dt', 'dl', 'tbody', 'thead', 'tfoot',
              'table', 'td', 'th', 'tr', 'colgroup', 'fieldset', 'legend','body']

    static final Map attributePatterns =[
          style:[pattern:null, global:true, elements:[]],
          id: [ pattern: HTML_ID,global:true, elements:[]],
          'class': [ pattern: HTML_CLASS,global:true, elements:[]],
          lang: [ pattern: LANG,global:true, elements:[]],
          title: [ pattern: HTML_TITLE,global:true, elements:[]],
          src:[ pattern: ONSITE_OR_OFFSITE_URL, global:false, elements:['img']],
          axis: [pattern: NAME, global: false, elements: ['td', 'th']],
          headers: [pattern: NAME, global: false, elements: ['td', 'th']],
          face: [pattern: FACE, global: false, elements: ['font']],
          nohref : [pattern:BLANK, global: false, elements: ['a']],
          name : [pattern:NAME, global: false, elements: ['a','img']],
          border : [pattern:NUMBER, global: false, elements: ['img']],
          hspace : [pattern:NUMBER, global: false, elements: ['img']],
          vspace : [pattern:NUMBER, global: false, elements: ['img']],
          border : [pattern:NUMBER, global: false, elements: ['table']],
          cellpadding : [pattern:NUMBER, global: false, elements: ['table']],
          cellspacing : [pattern:NUMBER, global: false, elements: ['table']],
          noresize : [pattern:NO_RESIZE, global: false, elements: ['table']],
          scope : [pattern:SCOPE, global: false, elements: ['td', 'th']],
          nowrap : [pattern: BLANK,global: false, elements: ['td', 'th']],
          height : [pattern:NUMBER_OR_PERCENT, global: false, elements: ['table', 'td', 'th', 'tr', 'img']],
          width : [pattern:NUMBER_OR_PERCENT, global: false, elements: ['table', 'td', 'th', 'tr', 'img', 'colgroup', 'col']],
          colspan : [pattern:NUMBER, global: false, elements: ['td', 'th']],
          rowspan : [pattern:NUMBER, global: false, elements: ['td', 'th']],
          align:[patern:ALIGN, global: false, elements:['p','thead', 'tbody', 'tfoot', 'img','td', 'th', 'tr', 'colgroup', 'col']],
          'for':[patern:HTML_ID, global: false, elements:['label']],
          color:[patern:COLOR_NAME_OR_COLOR_CODE, global: false, elements:['font']],
          size:[patern:NUMBER, global: false, elements:['font']],
          href:[patern:ONSITE_OR_OFFSITE_URL, global: false, elements:['a']],
          alt:[patern:PARAGRAPH, global: false, elements:['img']],
          bgcolor:[patern:COLOR_NAME_OR_COLOR_CODE, global: false, elements:['table','td', 'th']],
          background:[patern:ONSITE_URL, global: false, elements:['table','td', 'th', 'tr']],
          align:[patern:ALIGN, global: false, elements:['table']],
          abbr:[patern:PARAGRAPH, global: false, elements:['td', 'th']],
          valign:[patern:VALIGN, global: false, elements:['thead', 'tbody', 'tfoot','td', 'th', 'tr', 'colgroup', 'col']],
          charoff:[patern:NUMBER_OR_PERCENT, global: false, elements:['td', 'th', 'tr', 'colgroup', 'col','thead', 'tbody', 'tfoot']],
          'char':[patern:ONE_CHAR, global: false, elements:['td', 'th', 'tr', 'colgroup', 'col','thead', 'tbody', 'tfoot']]
    ]

    static final Set<String> allowedAttributes = attributePatterns.keySet()

    static final Pattern PATTERN = Pattern.compile('') //TODO just a stand in so the code compiles till I replace all the css patterns
    static final Map cssPropertyPatterns = [
       'background': PATTERN,
       'background-attachment': FIXED_OR_SCROLL,
       'background-color': Pattern.compile("${INITIAL_INHERIRIT_TRANSPARENT.pattern()}|${COLOR_NAME_OR_COLOR_CODE.pattern()}"),
       'background-image': CSS_URL,
       'background-position':  Pattern.compile("${POSITION.pattern()}|${POSITIONS.pattern()}"),
       'background-repeat': REPEAT,
       'border': PATTERN,
       'border-bottom': PATTERN,
       'border-bottom-color': COLOR_NAME_OR_COLOR_CODE,
       'border-bottom-style': BORDER_STYLE,
       'border-bottom-width': Pattern.compile("${BORDER_WIDTH.pattern()}|${LENGTH.pattern()}"),
       'border-color': COLOR_NAME_OR_COLOR_CODE,
       'border-left': PATTERN,
       'border-left-color': COLOR_NAME_OR_COLOR_CODE,
       'border-left-style': BORDER_STYLE,
       'border-left-width': Pattern.compile("${BORDER_WIDTH.pattern()}|${LENGTH.pattern()}"),
       'border-right': PATTERN,
       'border-right-color': COLOR_NAME_OR_COLOR_CODE,
       'border-right-style': BORDER_STYLE,
       'border-right-width': Pattern.compile("${BORDER_WIDTH.pattern()}|${LENGTH.pattern()}"),
       'border-style': BORDER_STYLE,
       'border-top': PATTERN,
       'border-top-color': COLOR_NAME_OR_COLOR_CODE,
       'border-top-style': BORDER_STYLE,
       'border-top-width': Pattern.compile("${BORDER_WIDTH.pattern()}|${LENGTH.pattern()}"),
       'border-width': Pattern.compile("${BORDER_WIDTH.pattern()}|${LENGTH.pattern()}"),
       'clear': PATTERN,
       'cursor': PATTERN,
       'display': PATTERN,
       'float': PATTERN,
       'position': PATTERN,
       'visibility': PATTERN,
       'height': AUTO_INITIAL_OR_LENGTH,
       'line-height': PATTERN,
       'max-height': PATTERN,
       'max-width': PATTERN,
       'min-height': PATTERN,
       'min-width': PATTERN,
       'width': AUTO_INITIAL_OR_LENGTH,
       'font': PATTERN,
       'font-family': PATTERN,
       'font-size': PATTERN,
       'font-size-adjust': PATTERN,
       'font-stretch': PATTERN,
       'font-style': PATTERN,
       'font-variant': PATTERN,
       'font-weight': PATTERN,
       'content': PATTERN,
       'counter-increment': PATTERN,
       'counter-reset': PATTERN,
       'quotes': PATTERN,
       'list-style': PATTERN,
       'list-style-image': PATTERN,
       'list-style-position': PATTERN,
       'list-style-type': PATTERN,
       'marker-offset': PATTERN,
       'margin': PATTERN,
       'margin-bottom': PATTERN,
       'margin-left': PATTERN,
       'margin-right': PATTERN,
       'margin-top': PATTERN,
       'outline': PATTERN,
       'outline-color': COLOR_NAME_OR_COLOR_CODE,
       'outline-style': PATTERN,
       'outline-width': PATTERN,
       'padding': PATTERN,
       'padding-bottom': PATTERN,
       'padding-left': PATTERN,
       'padding-right': PATTERN,
       'padding-top': PATTERN,
       'bottom': PATTERN,
       'clip': PATTERN,
       'left': PATTERN,
       'overflow': PATTERN,
       'position': PATTERN,
       'right': PATTERN,
       'top': PATTERN,
       'vertical-align': PATTERN,
       'z-index': PATTERN,
       'border-collapse': PATTERN,
       'border-spacing': PATTERN,
       'caption-side': PATTERN,
       'empty-cells': PATTERN,
       'table-layout': PATTERN,
       'color': COLOR_NAME_OR_COLOR_CODE,
       'direction': PATTERN,
       'line-height': PATTERN,
       'letter-spacing': PATTERN,
       'text-align': PATTERN,
       'text-decoration': PATTERN,
       'text-indent': PATTERN,
       'text-shadow': PATTERN,
       'text-transform': PATTERN,
       'unicode-bidi': PATTERN,
       'white-space': PATTERN,
       'word-spacing': PATTERN,
       'all': PATTERN,
       'aural': PATTERN,
       'braille': PATTERN,
       'embossed': PATTERN,
       'handheld': PATTERN,
       'print': PATTERN,
       'projection': PATTERN,
       'screen': PATTERN,
       'tty': PATTERN,
       'tv': PATTERN
   ]

   static final Set<String> allowedCssProperties = cssPropertyPatterns.keySet()

}
