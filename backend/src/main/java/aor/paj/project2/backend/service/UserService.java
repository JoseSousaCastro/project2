package aor.paj.project2.backend.service;

import aor.paj.project2.backend.bean.UserBean;
import aor.paj.project2.backend.dto.Task;
import aor.paj.project2.backend.dto.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
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
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(User user) {

        Response response;


        boolean isUsernameAvailable = userBean.isUsernameAvailable(user.getUsername());
        if (!isUsernameAvailable) {
            response = Response.status(Response.Status.CONFLICT).entity("Username already in use").build();
        } else {
            boolean createUser = userBean.addUser(user);
            if (createUser) {
                response = Response.status(Response.Status.CREATED).entity("User registred successfully").build();
            } else {
                response = Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong").build();
            }
        }
        return response;
    }


    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username")String username) {
        User user = userBean.getUser(username);
        if (user==null)
            return Response.status(Response.Status.NOT_FOUND).entity("User with this username was not found").build();

        return Response.ok().entity(user).build();
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

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {

        Response response;
        boolean isAuth = userBean.isAuthenticated(username, password);

        if (isAuth) {
            response = Response.status(200).entity("login successful").build();
        } else {
            response = Response.status(401).entity("Invalid credentials").build();
        }
        return response;
    }

    @GET
    @Path("/username/{username}/availability")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyUsernameAvailability(@PathParam("username") String username) {

        Response response;

        boolean isUsernameAvailable = userBean.isUsernameAvailable(username);

        if (isUsernameAvailable) {
            response = Response.status(200).entity("Username available").build();
        } else {
            response = Response.status(404).entity("Username already in use").build();
        }
        return response;
    }

    @GET
    @Path("/{username}/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsersTasks(@PathParam("username") String username) {

        Response response;
        ArrayList<Task> userTasks = userBean.getUserTasks(username);

        if (userTasks == null) {
            response = Response.status(Response.Status.BAD_REQUEST).entity("No task list to return").build();
        } else {
            response = Response.status(Response.Status.OK).entity("List of tasks returned successfully").build();
        }
        return response;
    }
}
