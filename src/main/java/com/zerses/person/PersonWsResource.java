package com.zerses.person;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.zerses.canonical.PersonFindRequest;

@Path("/") 
@WebService
public interface PersonWsResource {
	
	@POST
	@Path("/personCxf/find")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response methodA(PersonFindRequest req);
}
