HTML Enforcer
-------------------------------
This Library is a first attempt, at making an enforcer/validator for Html rather than a sanitizer. Current libraries I've found don't
 cut it like Anti-Samy(no longer supported) and OWASP Java HTML Sanitizer which 
 leans toward sanitizing, changing the original html, making it hard to detect if 
 xxs was removed, which leads to false positives. So my idea it to parse through
 all the elements and attributes seeing if they are allowed, also I would add checks
 based on regexs for values. I'm thinking of adapting something like the 
 htmlPolicy.groovy(originally an example from Java HTML Sanitizer), but using
 Groovy ConfigSlurper syntax for configuration:
 
 https://github.com/OWASP/java-html-sanitizer/blob/master/src/main/java/org/owasp/html/examples/EbayPolicyExample.java

For now I'm still working on the default config, adding Regexes for whitelisting CSS.


For documentation see the github page:
[documentation](https://virtualdogbert.github.io/HtmlEnforcer/)

