package edu.school21.cinema.servlets;

import edu.school21.cinema.exception.FwaRuntimeException;
import edu.school21.cinema.services.UserService;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1202847501603569965L;

	private UserService userService;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Map<String, String[]> params = req.getParameterMap();
		try {
			userService.createUser(params.get("email")[0],
					params.get("firstName")[0],
					params.get("lastName")[0],
					params.get("password")[0]);
			resp.sendRedirect(req.getContextPath() + "/signIn");
		} catch (FwaRuntimeException e) {
			Map<String, String> errors = e.getErrors();
			Map<String, String> fields = new HashMap<>();

			if (!errors.containsKey("firstName")) {
				fields.put("firstName", params.get("firstName")[0]);
			}
			if (!errors.containsKey("lastName")) {
				fields.put("lastName", params.get("lastName")[0]);
			}
			if (!errors.containsKey("email")) {
				fields.put("email", params.get("email")[0]);
			}

			resp.setStatus(e.getStatusCode());
			req.setAttribute("fields", fields);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/signUpForm").forward(req, resp);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("fields", new HashMap<>());
		req.setAttribute("errors", new HashMap<>());
		req.getRequestDispatcher("/signUpForm").forward(req, resp);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ApplicationContext context = (ApplicationContext) config.getServletContext().getAttribute("springContext");
		this.userService = context.getBean(UserService.class);
	}
}
