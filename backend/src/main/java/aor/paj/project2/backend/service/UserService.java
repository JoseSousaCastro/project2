package aor.paj.project2.backend.service;

import aor.paj.project2.backend.bean.UserBean;
import aor.paj.project2.backend.dto.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users")
public class UserService {

    @Inject
    UserBean userBean;


    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return userBean.getUsers();
    }


    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        userBean.addUser(user);
        return Response.status(200).entity("A new user is created").build();
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username")String username) {
        User user = userBean.getUser(username);
        if (user==null)
            return Response.status(200).entity("User with this username is not found").build();

        return Response.status(200).entity(user).build();
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(User user) {
        boolean updated = userBean.updateUser(user);
        if (!updated)
            return Response.status(200).entity("User with this username is not found").build();

        return Response.status(200).entity("updated").build();
    }
}