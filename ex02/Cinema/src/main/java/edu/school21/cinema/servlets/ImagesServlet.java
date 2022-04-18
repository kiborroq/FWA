package edu.school21.cinema.servlets;

import edu.school21.cinema.exception.FwaRuntimeException;
import edu.school21.cinema.models.ImageFile;
import edu.school21.cinema.models.User;
import edu.school21.cinema.services.FileService;
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
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/images/*")
@MultipartConfig
public class ImagesServlet extends HttpServlet {

	private FileService fileService;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
			ImageFile imageFile = fileService.createFile(user, request.getPart("fileToUpload"));
			List<ImageFile> imageFiles = (List<ImageFile>) session.getAttribute("files");
			if (imageFiles == null) {
				imageFiles = new ArrayList<>();
			}
			imageFiles.add(imageFile);

			session.setAttribute("files", imageFiles);
		} catch (FwaRuntimeException e) {
			response.setStatus(e.getStatusCode());
			request.setAttribute("errors", e.getErrors());
		}

		request.getRequestDispatcher("/profileForm").forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UUID uuid = UUID.fromString(req.getPathInfo().split("/")[1]);
		ImageFile imageFile = fileService.getFile(uuid);

		if (imageFile == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.setContentType(MimeTypeUtils.TEXT_HTML_VALUE);
			IOUtils.copy(new FileReader("src/main/webapp/WEB-INF/html/NotFound.html"), resp.getWriter());
		} else {
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType(MimeTypeUtils.IMAGE_JPEG_VALUE);
			resp.setContentLength(imageFile.getSize().intValue());
			resp.addHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", imageFile.getName()));

			try (FileInputStream fis = new FileInputStream("src/main/resources/images/" + imageFile.getUuid())) {
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