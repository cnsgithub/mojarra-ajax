package main;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.UUID;
import java.util.regex.Pattern;

@Named
@RequestScoped
public class TestBean {
	public static String illegalXmlChar = "\u000C";
	private static String illegalXmlChars = getIllegalXmlChars();

	private String input;

	public String getRandom() {
		return UUID.randomUUID().toString();
	}

	public String getMessage() {
		return getRandom() + illegalXmlChars;
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

	public static String getIllegalXmlChars() {
		StringBuilder illegal = new StringBuilder();
		for(char c = Character.MIN_VALUE; c < Character.MAX_VALUE; c++) {
			// character ranges from https://www.w3.org/TR/REC-xml/#charsets
			if(c == '\u0009' || c == '\n' || c == '\r')
				continue;
			if(c > '\u0020' && c < '\uD7FF')
				continue;
			if(c > '\uE000' && c < '\uFFFD')
				continue;
			illegal.append(c);
		}
		var out =  illegal.toString();
		System.out.println(out);
		return out;
	}

	public static void main(String[] args) {
		System.out.println(getIllegalXmlChars().length());
	}
}
