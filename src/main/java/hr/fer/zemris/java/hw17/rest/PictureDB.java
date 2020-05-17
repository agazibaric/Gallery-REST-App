package hr.fer.zemris.java.hw17.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Class acts like data base of pictures;
 * It offers method for getting different informations
 * about pictures that data base contains.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
public class PictureDB {
	
	/**
	 * List of all pictures.
	 */
	private static List<Picture> pictures;
	/**
	 * Path to the file that contains pictures.
	 */
	public static Path PICTURES_PATH;
	
	/**
	 * Method returns set of all tags.
	 * 
	 * @return set of tags
	 */
	public static Set<String> getTags() {
		if (pictures == null) {
			initPictures();
		}
		
		Set<String> tags = new TreeSet<>();
		pictures.forEach(p -> tags.addAll(p.getTags()));
		return tags;
	}
	
	/**
	 * Method returns list of pictures that have given {@code tag}.
	 * 
	 * @param tag tag of picture
	 * @return    list of pictures that have given {@code tag}
	 */
	public static List<Picture> getPicturesWithTag(String tag) {
		if (pictures == null) {
			initPictures();
		}
		return pictures.stream()
				.filter(p -> p.getTags().contains(tag))
				.collect(Collectors.toList());
	}
	
	/**
	 * Method initializes {@link #pictures}.
	 */
	private static void initPictures() {
		try {
			pictures = new ArrayList<>();
			List<String> lines = Files.readAllLines(PICTURES_PATH);
			for (int i = 0, n = lines.size(); i < n;) {
				pictures.add(getPictureFrom(lines.get(i), lines.get(i + 1), lines.get(i + 2)));
				i += 3;
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method returns new {@link Picture} object from given arguments.
	 * 
	 * @param fileName    file name of the picture
	 * @param pictureName picture's name
	 * @param tagString   string that contains picture's tags separated by comma
	 * @return		      {@link Picture} object
	 */
	private static Picture getPictureFrom(String fileName, String pictureName, String tagString) {
		List<String> tags = Arrays.asList(tagString.split(","));
		tags = tags.stream()
				.map(t -> t.trim())
				.collect(Collectors.toList());
		
		return new Picture(fileName, pictureName, tags);
	}
	
	/**
	 * Method returns picture names that contains given {@code tag}.
	 * 
	 * @param tag tag of picture
	 * @return    list of picture names that contains given {@code tag}
	 */
	public static List<String> getPictureNamesWithTag(String tag) {
		if (pictures == null) {
			initPictures();
		}
		return pictures.stream()
				.filter(p -> p.getTags().contains(tag))
				.map(p -> p.getFileName())
				.collect(Collectors.toList());
	}
	
	/**
	 * Method returns {@link Picture} object that has given {@code name} which is unique.
	 * 
	 * @param name name of  the picture
	 * @return     {@link Picture} object that has given {@code name} or
	 * 			   {@code null} ih there's no such picture
	 */
	public static Picture getPictureWithName(String name) {
		if (pictures == null) {
			initPictures();
		}

		for (Picture picture : pictures) {
			if (picture.getFileName().equals(name)) {
				return picture;
			}
		}
		return null;
	}
	
}
