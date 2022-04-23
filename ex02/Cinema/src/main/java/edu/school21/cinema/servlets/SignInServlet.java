package edu.school21.cinema.servlets;

import edu.school21.cinema.models.User;
import edu.school21.cinema.services.SessionService;
import edu.school21.cinema.services.UserService;
import edu.school21.cinema.util.Util;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {

	private static final long serialVersionUID = -1645768609963439573L;

	private UserService userService;
	private SessionService sessionService;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Map<String, String[]> params = req.getParameterMap();

		User user = userService.login(params.get("email")[0], params.get("password")[0]);
		if (user == null) {
			Map<String, String> errors = new HashMap<>();
			errors.put("commonError", "Email or password incorrect");
			Map<String, String> fields = new HashMap<>();
			fields.put("email", params.get("email")[0]);

			req.setAttribute("errors", errors);
			req.setAttribute("fields", fields);

			resp.setStatus(SC_BAD_REQUEST);
			req.getRequestDispatcher("/signInForm").forward(req, resp);
		} else {
			HttpSession session = req.getSession();
			session.setAttribute("user", user);

			sessionService.createSession(user, Util.getClientIpAddress(req));

			resp.sendRedirect(req.getContextPath() + "/profile");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("errors", new HashMap<>());
		req.setAttribute("fields", new HashMap<>());
		req.getRequestDispatcher("/signInForm").forward(req, resp);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ApplicationContext context = (ApplicationContext) config.getServletContext().getAttribute("springContext");
		this.userService = context.getBean(UserService.class);
		this.sessionService = context.getBean(SessionService.class);
	}
}
