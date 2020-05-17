package hr.fer.zemris.java.hw17.rest;

import java.util.List;

/**
 * Class represent picture that has file name, picture name and tags that are associated to the picture.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
public class Picture {
	
	/**
	 * Picture's file name.
	 */
	private String fileName;
	/**
	 * Picture's name
	 */
	private String pictureName;
	/**
	 * Picture's tags.
	 */
	private List<String> tags;
	
	/**
	 * Constructor.
	 * 
	 * @param fileName    {@link #fileName}
	 * @param pictureName {@link #pictureName}
	 * @param tags 		  {@link #tags}
	 */
	public Picture(String fileName, String pictureName, List<String> tags) {
		this.fileName = fileName;
		this.pictureName = pictureName;
		this.tags = tags;
	}

	/**
	 * Method returns picture's file name.
	 * 
	 * @return picture's file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Method returns picture's name.
	 * 
	 * @return picture's name
	 */
	public String getPictureName() {
		return pictureName;
	}

	/**
	 * Method returns picture's tags.
	 * 
	 * @return picture's tags
	 */
	public List<String> getTags() {
		return tags;
	}

}