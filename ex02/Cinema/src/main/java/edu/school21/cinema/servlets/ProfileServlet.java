package edu.school21.cinema.servlets;

import edu.school21.cinema.models.ImageFile;
import edu.school21.cinema.models.User;
import edu.school21.cinema.services.FileService;
import edu.school21.cinema.services.SessionService;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

	private static final long serialVersionUID = -2061667219804457452L;

	private FileService fileService;
	private SessionService sessionService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		User user = (User) session.getAttribute("user");

		List<ImageFile> imageFiles = fileService.findAllFiles(user);
		session.setAttribute("files", imageFiles);

		List<SessionService.SessionDto> sessions = sessionService.getSessions(user);
		session.setAttribute("sessions", sessions);

		req.getRequestDispatcher("/profileForm").forward(req, resp);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ApplicationContext context = (ApplicationContext) config.getServletContext().getAttribute("springContext");
		this.fileService = context.getBean(FileService.class);
		this.sessionService = context.getBean(SessionService.class);
	}
}
