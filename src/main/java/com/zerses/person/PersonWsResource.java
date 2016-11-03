package com.zerses.person;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.zerses.canonical.PersonAddRequest;

@Path("/persons2")
@WebService
public interface PersonWsResource {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response methodA(@QueryParam("ageMin") Integer ageMin, @QueryParam("ageMax") Integer ageMax, @QueryParam("name") String name);
    
    @GET
    @Path("/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response methodA(@PathParam("personId") Integer personId);
    
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(PersonAddRequest req);
    
}
