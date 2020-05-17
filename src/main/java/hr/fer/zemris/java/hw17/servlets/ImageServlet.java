package hr.fer.zemris.java.hw17.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web servlet that creates image in full size.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebServlet("/servlets/fullImage")
public class ImageServlet extends HttpServlet {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Relative path of directory that contains all pictures.
	 */
	private static final String PICTURES_PATH = "/WEB-INF/slike";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Path picturesPath = Paths.get(req.getServletContext().getRealPath(PICTURES_PATH));
		
		String name = req.getParameter("name");
		if (name == null)
			return;
		name = name.trim();
		if (name.isEmpty())
			return;
		
		BufferedImage thumbnailImage = ServletsUtil.getImage(name, picturesPath);
		try {
			OutputStream os = resp.getOutputStream();
			ImageIO.write(thumbnailImage, "jpg", os);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
