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

