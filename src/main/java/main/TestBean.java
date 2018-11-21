package main;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.UUID;

@Named
@RequestScoped
public class TestBean {
	public static String illegalXmlChar = "\u000C";

	private String input;

	public String getRandom() {
		return UUID.randomUUID().toString();
	}

	public String getMessage() {
		return getRandom() + illegalXmlChar;
	}


	public void handleClick() {
		System.out.println("Button clicked");
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
}
