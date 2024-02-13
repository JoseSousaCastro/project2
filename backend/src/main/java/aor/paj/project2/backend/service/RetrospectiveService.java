package aor.paj.project2.backend.service;

import aor.paj.project2.backend.bean.RetrospectiveBean;
import aor.paj.project2.backend.dto.Retrospective;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/retrospectives")
public class RetrospectiveService {

    @Inject
    RetrospectiveBean retrospectiveBean;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Retrospective> getRetrospectives() {
        return retrospectiveBean.getRetrospectives();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newRetrospective(Retrospective temporaryRetrospective) {
        retrospectiveBean.addRetrospective(temporaryRetrospective);
        return Response.status(201).entity("Retrospective created successfuly").build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRetrospective(@PathParam("id") String id) {
        Retrospective retrospective = retrospectiveBean.getRetrospective(id);
        if (retrospective == null) {
            return Response.status(200).entity("Retrospective with this id is not found").build();
        }
        return Response.status(200).entity(retrospective).build();
    }



}
