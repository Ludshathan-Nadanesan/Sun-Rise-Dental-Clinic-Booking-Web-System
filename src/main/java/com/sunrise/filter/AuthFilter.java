package com.sunrise.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {
		"/admin/*",
		"/receptionist/*"
})
public class AuthFilter implements Filter {

	@Override
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		// Prevent browser caching
		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		res.setHeader("Pragma", "no-cache");
		res.setDateHeader("Expires", 0);

		// Don't create a new session
		HttpSession session = req.getSession(false);

		// No session -> Login page
		if (session == null || session.getAttribute("role") == null) {

			res.sendRedirect(req.getContextPath() + "/");
			return;
		}

		String role = session.getAttribute("role").toString();

		String uri = req.getServletPath();
		
		// Receptionist trying to access Admin pages
		if (uri.contains("/admin/")
				&& !role.equalsIgnoreCase("admin")) {

//			res.sendRedirect(req.getContextPath() + "/index.jsp");
			redirectToLogin(req, res);
			return;
		}

		// Admin trying to access Receptionist pages
		if (uri.contains("/receptionist/")
				&& !role.equalsIgnoreCase("receptionist")) {

//			res.sendRedirect(req.getContextPath() + "/index.jsp");
			redirectToLogin(req, res);
			return;
		}

		// Everything OK
		chain.doFilter(request, response);

	}
	
	private void redirectToLogin(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.sendRedirect(req.getContextPath() + "/");
	}
	
}