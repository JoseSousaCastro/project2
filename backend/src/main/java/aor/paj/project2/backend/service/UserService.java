package aor.paj.project2.backend.service;

import aor.paj.project2.backend.bean.UserBean;
import aor.paj.project2.backend.bean.TaskBean;
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
    @Inject
    TaskBean taskBean;


    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return userBean.getUsers();
    }


    @PUT
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("username")String username, User user) {
        Response response;

        boolean updatedUser = userBean.updateUser(user);

        if (!updatedUser) {
            response = Response.status(Response.Status.NOT_FOUND).entity("User with this username is not found").build();
        } else {
            User updatedUserDetails = userBean.getUser(username);
            response = Response.status(Response.Status.OK).entity(updatedUserDetails).build(); //status code 200
        }
        return response;
    }


    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username")String username) {
        Response response;
        User user = userBean.getUser(username);
        if (user == null) {
            response = Response.status(Response.Status.NOT_FOUND).entity("User with this username was not found").build();
        } else {
            response = Response.ok().entity(user).build();
        }
        return response;
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@HeaderParam("username") String username, @HeaderParam("password") String password) {

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
            response = Response.status(Response.Status.OK).entity(userTasks).build();
        }
        return response;
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(User user){
        Response response;

        boolean isUsernameAvailable = userBean.isUsernameAvailable(user.getUsername());
        boolean isEmailValid = userBean.isEmailValid(user.getEmail());
        boolean isFieldEmpty = userBean.isAnyFieldEmpty(user);
        boolean isPhoneNumberValid = userBean.isPhoneNumberValid(user.getPhone());
        boolean isImageValid = userBean.isImageUrlValid(user.getPhotoURL());

        if (isFieldEmpty) {
            response = Response.status(422).entity("There's an empty field, fill all values").build();
        } else if (!isEmailValid) {
            response = Response.status(Response.Status.NOT_ACCEPTABLE).entity("Invalid email, try again").build();
        } else if (!isUsernameAvailable) {
            response = Response.status(Response.Status.CONFLICT).entity("Username already in use").build(); //status code 409
        } else if (!isImageValid) {
            response = Response.status(Response.Status.BAD_REQUEST).entity("Image URL invalid").build();
        }else if (!isPhoneNumberValid) {
            response = Response.status(422).entity("Invalid phone number").build();
        } else if(userBean.addUser(user)) {
            response = Response.status(Response.Status.CREATED).entity("User registered successfully").build(); //status code 201
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong").build(); //status code 400
        }
        return response;
    }

    @POST
    @Path("/{username}/addTask")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newTask(@HeaderParam("username") String username, @HeaderParam("password") String password, Task task) {
        Response response;
        if(!userBean.isAuthenticated(username, password)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            userBean.getUserTasks(username).add(task);
            taskBean.addTask(task);
            response = Response.status(201).entity("Task created successfully").build();
        }

        return response;
    }

    @PUT
    @Path("/{username}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newTask(@HeaderParam("username") String username, @HeaderParam("password") String password, @PathParam("id") String id, Task task) {
        Response response;
        if (!userBean.isAuthenticated(username, password)) {
            response = Response.status(401).entity("Invalid credentials").build();
        } else {
            boolean updated = taskBean.updateTask(userBean, username, id, task);
            if (!updated) {
                response = Response.status(404).entity("Task with this id is not found").build();
            } else {
                response = Response.status(200).entity("updated").build();
            }
        }
        return response;
    }

}
