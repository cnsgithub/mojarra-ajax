# myfaces-ajax
Minimal demo to reproduce some issues with myfaces partial update.

To reproduce just run
````bash
mvn clean package jetty:run
````
and wait for the server to start up.

## Issue: [Ajax update fails due to invalid characters in response XML (DoS)](https://issues.apache.org/jira/browse/MYFACES-4266)

Open the url <http://localhost:8080/index.xhtml>.

It also works for user supplied inputs, open <http://localhost:8080/input.xhtml> and enter the contents of the ``illegal-xml-chars.txt`` file.

Workaround is available at https://github.com/cnsgithub/mojarra-ajax/blob/master/src/main/java/main/IllegalXmlCharactersFilter.java.

Pull request https://github.com/apache/myfaces/pull/27 has been merged into all MyFaces branches. Stay tuned for new release versions.

## Issue: [inputText: lack of user input validation (maxlength)](https://issues.apache.org/jira/browse/MYFACES-4279)

Open the url <http://localhost:8080/maxlength.xhtml> and click the _Hack_ button
