package edu.school21.cinema.services;

import edu.school21.cinema.exception.FwaRuntimeException;
import edu.school21.cinema.models.ImageFile;
import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.FileRepository;
import edu.school21.cinema.repositories.UserRepository;
import edu.school21.cinema.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE;
import static javax.servlet.http.HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;

@Slf4j
@Service
public class FileService {

	private final static int MAX_FILE_SIZE = 10000000;
	private final static File UPLOAD_DIR;

	static {
		UPLOAD_DIR = new File(PropertiesUtil.getProperty("cinema.path.image-upload"));
		if (!UPLOAD_DIR.exists()) {
			UPLOAD_DIR.mkdirs();
		}
	}

	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private UserRepository userRepository;

	public ImageFile createFile(User user, Part filePart) {
		validateFile(filePart);

		ImageFile uploadedImageFile = new ImageFile();
		uploadedImageFile.setUuid(UUID.randomUUID());
		uploadedImageFile.setName(getFileName(filePart));
		uploadedImageFile.setSize(filePart.getSize());
		uploadedImageFile.setMime(filePart.getContentType());
		uploadedImageFile.setUserId(user.getId());
		uploadedImageFile.setDate(LocalDateTime.now());

		try (FileOutputStream fos = new FileOutputStream(UPLOAD_DIR.getPath() + File.separator + uploadedImageFile.getUuid())) {
			IOUtils.copy(filePart.getInputStream(), fos);
		} catch (Exception e) {
			throw new FwaRuntimeException("Error has occured during file uploading", SC_INTERNAL_SERVER_ERROR, e);
		}

		Long id = fileRepository.saveFile(uploadedImageFile);
		uploadedImageFile.setId(id);

		user.setAvatar(uploadedImageFile);
		userRepository.updateAvatar(user);

		return uploadedImageFile;
	}

	public ImageFile getFile(UUID uuid) {
		return fileRepository.findFileByUuid(uuid);
	}

	public List<ImageFile> findAllFiles(User user) {
		return fileRepository.findAllByUserId(user.getId());
	}

	private void validateFile(Part file) {
		String mime = file.getContentType();
		long size = file.getSize();

		if (StringUtils.isEmpty(mime)
				|| (!mime.equalsIgnoreCase(MimeTypeUtils.IMAGE_JPEG_VALUE)
				&& !mime.equalsIgnoreCase(MimeTypeUtils.IMAGE_PNG_VALUE))) {
			throw new FwaRuntimeException(String.format("Not supported file format '%s'", mime), SC_UNSUPPORTED_MEDIA_TYPE);
		}

		if (size > MAX_FILE_SIZE) {
			throw new FwaRuntimeException(String.format("Too large file size '%d'", size), SC_REQUEST_ENTITY_TOO_LARGE);
		}
	}

	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename"))
				return content.substring(content.indexOf("=") + 2, content.length() - 1);
		}
		return "image";
	}
}
