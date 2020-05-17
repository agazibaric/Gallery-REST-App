package hr.fer.zemris.java.hw17.servlets;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw17.rest.PictureDB;

/**
 * Class that initializes gallery database that contains all pictures.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebListener
public class GaleryListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Path path = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/opisnik.txt"));
		PictureDB.PICTURES_PATH = path;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}	

}
