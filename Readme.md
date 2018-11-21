Minimal demo of an invalid XML character issue with mojarra partial update.

To reproduce just run
````bash
mvn package wildfly:run
````

wait for the server to start up, then open the url <http://localhost:8080/ajax/index.xhtml>.

It also works for user supplied inputs, open <http://localhost:8080/ajax/input.xhtml> and enter the [\u000C](https://r12a.github.io/uniview/?charlist=%0C#title) character 
into the input field.  	
