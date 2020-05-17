package hr.fer.zemris.java.hw17.rest;

import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class uses library org.json for dealing with JSON format.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 */
@Path("/picture")
public class PictureJSON {

	/**
	 * Method returns picture tags in json array.
	 * 
	 * @return picture tags in json array
	 */
	@Path("/tags")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTags() {
		Set<String> tags = PictureDB.getTags();
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		tags.forEach(t -> array.put(t));
		
		result.put("tags", array);
		return Response.status(Status.OK).entity(result.toString()).build();
	}
	
	/**
	 * Method returns list of picture's names that has given {@code tag}.
	 * 
	 * @param tag tag of picture
	 * @return    list of picture's names
	 */
	@Path("/thumbnails/{tag}")
	@GET
	public Response getPicturesWithTag(@PathParam("tag") String tag) {
		List<String> pictures = PictureDB.getPictureNamesWithTag(tag);
		
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		pictures.forEach(p -> array.put(p));
		result.put("tagPictures", array);
		
		return Response.status(Status.OK).entity(result.toString()).build();
	}
	
	/**
	 * Method returns {@link Picture} object with given {@code name}.
	 * 
	 * @param name name of the picture
	 * @return     {@link Picture} object with given {@code name}
	 */
	@Path("/picture/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Picture getPictures(@PathParam("name") String name) {
		return PictureDB.getPictureWithName(name);
	}

}
