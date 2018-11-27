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

    public IllegalXmlCharactersResponseWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(int c) {
        super.write(xmlEncode((char) c));
    }

    @Override
    public void write(char[] buf, int off, int len) {
        super.write(xmlEncode(buf), off, len);
    }

    @Override
    public void write(char[] buf) {
        super.write(xmlEncode(buf));
    }

    @Override
    public void write(String s, int off, int len) {
        super.write(xmlEncode(s.toCharArray()), off, len);
    }

    @Override
    public void write(String s) {
        super.write(xmlEncode(s.toCharArray()));
    }

    private char[] xmlEncode(char[] ca) {
        for (int i = 0; i < ca.length; i++) {
            ca[i] = xmlEncode(ca[i]);
        }
        return ca;
    }

    private char xmlEncode(char c) {
        if (Character.isSurrogate(c)) {
            return ' ';
        }
        if (c == '\u0009' || c == '\n' || c == '\r')
            return c;
        if (c > '\u0020' && c < '\uD7FF')
            return c;
        if (c > '\uE000' && c < '\uFFFD')
            return c;
        return ' ';
    }
}