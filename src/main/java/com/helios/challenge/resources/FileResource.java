package com.helios.challenge.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.helios.challenge.model.ErrorMessage;
import com.helios.challenge.service.MongoDbDocumentServiceImpl;
import com.helios.challenge.service.IDocumentService;

@Path("/files")
@Produces(MediaType.APPLICATION_JSON)
public class FileResource {
	
	IDocumentService databaseService = new MongoDbDocumentServiceImpl();
	
	@GET
	public Response getAllFiles(){
		List<String> documentNames;
		documentNames = databaseService.getAllDocumentNames();
		return Response
				.status(Status.OK)
				.entity(documentNames)
				.build();
	}
	
	@GET
	@Path("/{filename}")
	public Response getFileByName(@PathParam("filename") String filename){
		String document = databaseService.getDocumentByName(filename);
		if(!document.isEmpty()){
		return Response
				.status(Status.OK)
				.entity(document)
				.build();
		} else {
			ErrorMessage errorMessage = new ErrorMessage(filename + " not found.", 400, "doc.helios.challenge.com/api/errors");
			throw new NotFoundException(errorMessage.getErrorMessage(), Response
					.status(Status.NOT_FOUND)
					.entity(errorMessage)
					.build());
		}
	}

}
