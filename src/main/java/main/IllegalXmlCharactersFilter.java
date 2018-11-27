package main;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

@WebFilter(filterName = "IllegalXmlCharactersFilter")
public class IllegalXmlCharactersFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (resp instanceof HttpServletResponse && req instanceof HttpServletRequest) {
            String header = ((HttpServletRequest) req).getHeader("Faces-Request");
            if (header != null && header.equalsIgnoreCase("partial/ajax")) {
                resp = new IllegalXmlCharactersResponseWrapper((HttpServletResponse) resp);
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
    }

}

class IllegalXmlCharactersResponseWrapper extends HttpServletResponseWrapper {

    public IllegalXmlCharactersResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new IllegalXmlCharactersResponseWriter(super.getWriter());
    }
}

class IllegalXmlCharactersResponseWriter extends PrintWriter {
    private static final char[] EMPTY_CHAR_ARRAY = new char[0];
    private static final char BLANK_CHAR = ' ';

    public IllegalXmlCharactersResponseWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(int c) {
        if (isInvalidChar((char) c)) {
            super.write((int) BLANK_CHAR);
        }
        else {
            super.write(c);
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) {
        super.write(encodeCharArray(cbuf, off, len), off, len);
    }

    @Override
    public void write(String str, int off, int len) {
        super.write(encodeString(str, off, len), off, len);
    }

    private static String encodeString(String str, int off, int len) {
        if (str == null) {
            return null;
        }

        boolean containsInvalidChar = false;
        char[] encodedCharArray = EMPTY_CHAR_ARRAY;

        for (int i = off; i < off + len; i++) {
            if (isInvalidChar(str.charAt(i))) {
                if (!containsInvalidChar) {
                    containsInvalidChar = true;
                    encodedCharArray = str.toCharArray();
                }
                encodedCharArray[i] = BLANK_CHAR;
            }
        }

        if (containsInvalidChar) {
            return String.valueOf(encodedCharArray);
        }

        return str;
    }

    private static char[] encodeCharArray(char[] cbuf, int off, int len) {
        if (cbuf == null) {
            return null;
        }

        for (int i = off; i < off + len; i++) {
            if (isInvalidChar(cbuf[i])) {
                cbuf[i] = BLANK_CHAR;
            }
        }
        return cbuf;
    }

    private static boolean isInvalidChar(char c) {
        if (Character.isSurrogate(c)) {
            return true;
        }
        if (c == '\u0009' || c == '\n' || c == '\r') {
            return false;
        }
        if (c > '\u0020' && c < '\uD7FF') {
            return false;
        }
        if (c > '\uE000' && c < '\uFFFD') {
            return false;
        }
        return true;
    }
}