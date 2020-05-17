package hr.fer.zemris.java.hw17.servlets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web servlet that creates thumbnail image that has width = 150 and height = 150.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebServlet("/servlets/thumbnail")
public class ThumbnailServlet extends HttpServlet {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Relative path to the thumbnail directory.
	 */
	private static final String THUMBNAILS_PATH = "/WEB-INF/thumbnails";
	/**
	 * Relative path to the picture's directory.
	 */
	private static final String PICTURES_PATH = "/WEB-INF/slike";
	/**
	 * Width of thumbnail image.
	 */
	private static final Integer THUMBNAIL_WIDTH = 150;
	/**
	 * Height of thumbnail image.
	 */
	private static final Integer THUMBNAIL_HEIGHT = 150;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Path thumbnailPath = Paths.get(req.getServletContext().getRealPath(THUMBNAILS_PATH));
		Path picturesPath = Paths.get(req.getServletContext().getRealPath(PICTURES_PATH));
		
		checkThumbnailsDirectory(thumbnailPath);
		
		String name = req.getParameter("name");
		if (name == null)
			return;
		final String imageName = name.trim();
		if (imageName.isEmpty())
			return;
		
		
		try (Stream<Path> paths = Files.walk(thumbnailPath)) {
			if (!paths.filter(p -> p.getFileName().toString().equals(imageName))
					 .findAny()
					 .isPresent())
				createThumbnail(imageName, thumbnailPath, picturesPath);
		}
		
		BufferedImage thumbnailImage = ServletsUtil.getImage(imageName, thumbnailPath);
		try {
			OutputStream os = resp.getOutputStream();
			ImageIO.write(thumbnailImage, "jpg", os);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method creates thumbnail image in thumbnail's directory 
	 * that is represented by given {@code thumbnailPath}.
	 * 
	 * @param imageName     name of image
	 * @param thumbnailPath path of the thumbnail's directory
	 * @param picturesPath  path of the picture's directory
	 */
	private void createThumbnail(String imageName, Path thumbnailPath, Path picturesPath) {
		BufferedImage originalImage = ServletsUtil.getImage(imageName, picturesPath);
		BufferedImage thumbnailImage = getResizedImage(originalImage);
		File output = thumbnailPath.resolve(imageName).toFile();
		try {
			ImageIO.write(thumbnailImage, "jpg", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method returns resized copy of given {@code originalImage}.
	 * 
	 * @param originalImage image from which resized copy is created
	 * @return			    resized copy of given {@code originalImage}
	 */
	private BufferedImage getResizedImage(Image originalImage) {
		BufferedImage thumbnail = new BufferedImage(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = thumbnail.createGraphics();
		g.drawImage(originalImage, 0, 0, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, null);
		g.dispose();
		return thumbnail;
	}
	
	/**
	 * Method creates thumbnail directory if it's not already created.
	 * 
	 * @param thumbnailPath path of the thumbnail directory
	 */
	private void checkThumbnailsDirectory(Path thumbnailPath) {
		if(!Files.exists(thumbnailPath)) {
			try {
				Files.createDirectories(thumbnailPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
