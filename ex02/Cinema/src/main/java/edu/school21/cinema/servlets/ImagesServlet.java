package edu.school21.cinema.servlets;

import edu.school21.cinema.exception.FwaRuntimeException;
import edu.school21.cinema.models.ImageFile;
import edu.school21.cinema.models.User;
import edu.school21.cinema.services.FileService;
import edu.school21.cinema.util.PropertiesUtil;
import edu.school21.cinema.util.Util;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/images/*")
@MultipartConfig
public class ImagesServlet extends HttpServlet {

	private static final long serialVersionUID = 5646812020786710363L;
	private static final String FILE_UPLOAD_PATH = PropertiesUtil.getProperty("cinema.path.image-upload");

	private FileService fileService;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
			fileService.createFile(user, request.getPart("fileToUpload"));
		} catch (FwaRuntimeException e) {
			response.setStatus(e.getStatusCode());
			request.setAttribute("errors", e.getErrors());
		}

		response.sendRedirect(request.getContextPath() + "/profile");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ImageFile imageFile = null;

		System.out.println(req.getPathInfo());

		if (req.getPathInfo() != null) {
			String [] pathParts = req.getPathInfo().split("/");

			if (pathParts.length == 2) {

				UUID uuid = Util.toUuid(pathParts[1]);
				if (uuid != null) {
					imageFile = fileService.getFile(uuid);
				}
			}
		}

		if (imageFile == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.setContentType(MimeTypeUtils.TEXT_HTML_VALUE);
			IOUtils.copy(new FileReader("src/main/webapp/WEB-INF/html/NotFound.html"), resp.getWriter());
		} else {
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType(MimeTypeUtils.IMAGE_JPEG_VALUE);
			resp.setContentLength(imageFile.getSize().intValue());
			resp.addHeader("Content-Disposition", String.format("filename=\"%s\"", imageFile.getName()));

			try (FileInputStream fis = new FileInputStream(FILE_UPLOAD_PATH + File.separator +imageFile.getUuid())) {
				IOUtils.copy(fis, resp.getOutputStream());
				resp.getOutputStream().close();
			}
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ApplicationContext context = (ApplicationContext) config.getServletContext().getAttribute("springContext");
		this.fileService = context.getBean(FileService.class);
	}
}