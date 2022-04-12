package edu.school21.cinema.filters;

import edu.school21.cinema.exception.FwaRuntimeException;
import org.apache.commons.io.IOUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;

@WebFilter(urlPatterns = "/profile")
public class AuthenticationFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (req.getSession().getAttribute("user") != null) {
			chain.doFilter(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_FORBIDDEN);
			res.setContentType("text/html");
			IOUtils.copy(new FileReader("src/main/webapp/WEB-INF/html/Forbidden.html"), res.getWriter());
		}
	}

	@Override
	public void destroy() {
	}
}
