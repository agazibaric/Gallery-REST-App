package hr.fer.zemris.java.hw17.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;


import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class that knows how to convert {@link Picture} object into application/json.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class PictureWriter implements MessageBodyWriter<Picture> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return type == Picture.class;
	}

	@Override
	public long getSize(Picture p, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return toData(p).length;
	}

	@Override
	public void writeTo(Picture p, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {

		entityStream.write(toData(p));
	}
	
	/**
	 * Method that converts picture into byte array.
	 * 
	 * @param p picture object
	 * @return  byte array that contains information of given picture
	 */
	private byte[] toData(Picture p) {
		String text;
		if (p == null) {
			text = "null;";
		} else {
			JSONObject result = new JSONObject();
			result.put("fileName", p.getFileName());
			result.put("pictureName", p.getPictureName());

			JSONArray topics = new JSONArray();
			for (String t : p.getTags()) {
				topics.put(t);
			}
			result.put("tags", topics);

			text = result.toString();
		}
		return text.getBytes(StandardCharsets.UTF_8);
	}
	
}
