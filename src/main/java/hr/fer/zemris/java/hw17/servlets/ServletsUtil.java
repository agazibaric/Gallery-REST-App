package hr.fer.zemris.java.hw17.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

/**
 * Class offers helper methods that can be used by gallery web servlets.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
public class ServletsUtil {
	
	/**
	 * Method returns {@link BufferedImage} from given {@code imageName} and {@code path}.
	 * 
	 * @param imageName name of image
	 * @param path		path of image
	 * @return			{@link BufferedImage} object or {@code null} if there's no such image
	 */
	public static BufferedImage getImage(String imageName, Path path) {
		try (InputStream is = Files.newInputStream(path.resolve(imageName))) {
			return ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
