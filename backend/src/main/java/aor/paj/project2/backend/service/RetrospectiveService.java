package aor.paj.project2.backend.service;

import aor.paj.project2.backend.bean.RetrospectiveBean;
import aor.paj.project2.backend.bean.UserBean;
import aor.paj.project2.backend.dto.Retrospective;
import aor.paj.project2.backend.dto.Comment;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/retrospective")
public class RetrospectiveService {

    @Inject
    RetrospectiveBean retrospectiveBean;
    @Inject
    UserBean userBean;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRetrospectives(@HeaderParam("username") String username, @HeaderParam("password") String password ) {
        Response response;
        if (!userBean.isAuthenticated(username, password)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            List<Retrospective> retrospectives = retrospectiveBean.getRetrospectives();
            response = Response.status(200).entity(retrospectives).build();
        }
        return response;
    }

    @GET
    @Path("/{id}/allComments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComments(@HeaderParam("username") String username, @HeaderParam("password") String password, @PathParam("id") String id) {
        Response response;
        if (!userBean.isAuthenticated(username, password)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            List<Comment> comments = retrospectiveBean.getComments(id);
            if (comments == null) {
                response = Response.status(404).entity("Retrospective with this id is not found").build();
            } else {
                response = Response.status(200).entity(comments).build();
            }
        }
        return response;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRetrospective(@HeaderParam("username") String username, @HeaderParam("password") String password, @PathParam("id") String id) {
        Response response;
        if (!userBean.isAuthenticated(username, password)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            Retrospective retrospective = retrospectiveBean.getRetrospective(id);
            if (retrospective == null) {
                response = Response.status(404).entity("Retrospective with this id is not found").build();
            } else {
                response = Response.status(200).entity(retrospective).build();
            }
        }
        return response;
    }

    @GET
    @Path("/{id}/comment/{id2}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComment(@HeaderParam("username") String username, @HeaderParam("password") String password, @PathParam("id") String id, @PathParam("id2") String id2) {
        Response response;
        if (!userBean.isAuthenticated(username, password)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            Comment comment = retrospectiveBean.getComment(id, id2);
            if (comment == null) {
                response = Response.status(404).entity("Comment with this id is not found").build();
            } else {
                response = Response.status(200).entity(comment).build();
            }
        }
        return response;
    }


    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newRetrospective(@HeaderParam("username") String username, @HeaderParam("password") String password, Retrospective temporaryRetrospective) {
        Response response;
        if (!userBean.isAuthenticated(username, password)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            retrospectiveBean.addRetrospective(temporaryRetrospective);
            response = Response.status(201).entity("Retrospective created successfuly").build();
        }
        return response;
    }


    @POST
    @Path("/{id}/addComment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newComment(@HeaderParam("username") String username, @HeaderParam("password") String password, @PathParam("id") String id, Comment temporaryComment) {
        Response response;
        if (!userBean.isAuthenticated(username, password)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            retrospectiveBean.addCommentToRetrospective(id, temporaryComment);
            response = Response.status(201).entity("Comment created successfuly").build();
        }
        return response;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeRetrospective(@HeaderParam("username") String username, @HeaderParam("password") String password, @PathParam("id") String id) {
        Response response;
        if (!userBean.isAuthenticated(username, password)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            boolean deleted = retrospectiveBean.deleteRetrospective(id);
            if (!deleted) {
                response = Response.status(404).entity("Retrospective with this id is not found").build();
            } else {
                response = Response.status(200).entity("deleted").build();
            }
        }
        return response;
    }

    @DELETE
    @Path("/{id}/deleteAllComments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeComments(@HeaderParam("username") String username, @HeaderParam("password") String password, @PathParam("id") String id, Comment temporaryComment) {
        Response response;
        if (!userBean.isAuthenticated(username, password)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            boolean deleted = retrospectiveBean.deleteAllComments(id);
            if (!deleted) {
                response = Response.status(404).entity("Retrospective with this id is not found").build();
            } else {
                response = Response.status(200).entity("deleted").build();
            }
        }
        return response;
    }

    @DELETE
    @Path("/{id}/comment/{id2}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeComment(@HeaderParam("username") String username, @HeaderParam("password") String password, @PathParam("id") String id, @PathParam("id2") String id2) {
        Response response;
        if (!userBean.isAuthenticated(username, password)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            boolean deleted = retrospectiveBean.deleteComment(id, id2);
            if (!deleted) {
                response = Response.status(404).entity("Comment with this id is not found").build();
            } else {
                response = Response.status(200).entity("deleted").build();
            }
        }
        return response;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRetrospective(@HeaderParam("username") String username, @HeaderParam("password") String password, @PathParam("id") String id, Retrospective temporaryRetrospective) {
        Response response;
        if (!userBean.isAuthenticated(username, password)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            boolean updated = retrospectiveBean.updateRetrospective(id, temporaryRetrospective);
            if (!updated) {
                response = Response.status(404).entity("Retrospective with this id is not found").build();
            } else {
                response = Response.status(200).entity("updated").build();
            }
        }
        return response;
    }

    @PUT
    @Path("/{id}/comment/{id2}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateComment(@HeaderParam("username") String username, @HeaderParam("password") String password, @PathParam("id") String id, @PathParam("id2") String id2, Comment temporaryComment) {
        Response response;
        if (!userBean.isAuthenticated(username, password)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            boolean updated = retrospectiveBean.updateComment(id, id2, temporaryComment);
            if (!updated) {
                response = Response.status(404).entity("Comment with this id is not found").build();
            } else {
                response = Response.status(200).entity("updated").build();
            }
        }
        return response;
    }
}
